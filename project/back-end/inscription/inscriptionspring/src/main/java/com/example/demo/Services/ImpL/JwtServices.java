package com.example.demo.Services.ImpL;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServices {
    private static final  String secret_key ="R1p1TVB1YmpDN2txZzE4Mm82YlVhblE2eHZtbmN5elE";//secret key to sign the token
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(
     Map<String,Object> extractClaims,
     UserDetails userDetails
    )
    {
       return Jwts
               .builder()
               .setClaims(extractClaims)//extra claims
               .setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))//10 hours
               .signWith(getsigningKey(), SignatureAlgorithm.HS256)
               .compact();

    }





    private Claims extractAllClaims(String jwt){
        return Jwts
                .parserBuilder()
                .setSigningKey(getsigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

    }

    public <T> T extractClaim(String jwt, Function<Claims,T>claimsResolver){
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

   public String ExtractUserEmail(String jwt){
        return extractClaim(jwt,Claims::getSubject); //get the subject from the token
    }
   private Key getsigningKey(){
        byte[] signingKeyByte = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(signingKeyByte);
   }

   public boolean isTokenValid(String jwt , UserDetails userDetails){
        final String userEmail = ExtractUserEmail(jwt);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
   }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }
    private Date extractExpiration(String jwt ){
        return extractClaim(jwt,Claims::getExpiration);
    }

}
