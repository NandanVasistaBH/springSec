package com.ndn.springSec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

        http.authorizeHttpRequests(request->request.anyRequest().authenticated()); // all req has to be authenticated
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
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

}
