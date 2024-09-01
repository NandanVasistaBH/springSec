package com.ndn.springSec.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // telling spring boss this is a configuration class use for that
@EnableWebSecurity // telling spring go with the config mentioned below not the default one
public class SecurityConfig {

    @Autowired(required = true)
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // return http.build(); just doing this is like disabling all the filters it as good as not having spring sec

        // diable csrf
        http.csrf(customizer->customizer.disable());
        http.authorizeHttpRequests(request->request
                                                    .requestMatchers("register","login")
                                                    .permitAll().
                                                    anyRequest().
                                                    authenticated()); // all req has to be authenticated except login and register
        // just making all request authenticated doesn't cut it we need to provide how we can login 

        // http.formLogin(Customizer.withDefaults()); // this will enable browser based login
        http.httpBasic(Customizer.withDefaults()); // for postman

        // as we have disabled csrf we need to make each req a new session to enhance sec by making each session stateless
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
        
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
