package com.example.demo.Execeptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2ExceptionHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException, IOException {
        // Handle the exception here
        if (exception instanceof OAuth2AuthenticationException) {
            // Redirect the user to the JWT authentication URL or any other action
            response.sendRedirect("/login?error=oauth2");
        } else {
            // Handle other types of exceptions
            response.sendRedirect("/login?error=true");
        }
    }
}
