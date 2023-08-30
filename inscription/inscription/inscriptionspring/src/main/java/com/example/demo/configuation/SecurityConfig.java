package com.example.demo.configuation;

import com.example.demo.Execeptions.OAuth2ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    private final JWTAUTHFILTER   jwtAuthFilter;
    @Autowired
    public  OAuth2ExceptionHandler OAuth2ExceptionHandler;

    private final AuthenticationProvider authencationProvider;

    @Bean//
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors() // Enable CORS configuration
                .configurationSource(corsConfigurationSource())
                .and()

                .authorizeRequests()
                .requestMatchers("/api/v2/inscription/**")//allow access to inscription endpoint without authentication
                .permitAll()//allow access to inscription endpoint without authentication
                .anyRequest()//allow access to other endpoints without authentication
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authencationProvider)//set custom authentication provider
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)








                ;

        return http.build();
    }


@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Add your frontend origin here
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Add any other allowed HTTP methods
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*"); // You can customize the allowed headers if needed
        configuration.addExposedHeader("Authorization"); // Expose the Authorization header to the frontend
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
};;;


