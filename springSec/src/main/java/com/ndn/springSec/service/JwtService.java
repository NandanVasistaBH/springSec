package com.ndn.springSec.service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import java.util.Base64; 
import java.util.Date; 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Map;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@Service
public class JwtService {
    private  static String secretKey;
    private static final String ALGO="HmacSHA256";
    public JwtService(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGO);
            SecretKey sk = keyGen.generateKey();
            this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        
    }
    
    public String generateToken(String username){
        Map<String,Object> claims= new HashMap<>();
        return Jwts.builder()
                    .claims()
                    .add(claims)
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+60*60*60)) // 1hr
                    .and()
                    .signWith(getKey())
                    .compact();
    }
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
