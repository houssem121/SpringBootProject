package com.example.demo.configuation;

import com.example.demo.Services.ImpL.JwtServices;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JWTAUTHFILTER extends OncePerRequestFilter {
    private final JwtServices JwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                   @Nonnull HttpServletResponse response,
                                  @Nonnull  FilterChain filterChain)//list of filters it will call the next filter in the chain
            throws ServletException, IOException {
            final String authorizationHeader = request.getHeader("Authorization");//contain our jwt token
            final String jwt ;
            final String userEmail ;

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;

    }
     jwt = authorizationHeader.substring(7);
     userEmail = JwtService.ExtractUserEmail(jwt);//get the email from the token
   if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){// check if the user is not authenticated
       UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
       if (JwtService.isTokenValid(jwt,userDetails)){
           UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(//create the authentication token and set it in the security context
                   userDetails,
                   null,
                   userDetails.getAuthorities());
           authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request) );


           SecurityContextHolder.getContext().setAuthentication(authToken);// set the authentication token in the security context holder so that it can be used by the spring security at this request
       }
   }
                filterChain.doFilter(request, response);
            }
}
