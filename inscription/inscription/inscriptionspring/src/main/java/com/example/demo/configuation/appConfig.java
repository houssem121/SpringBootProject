package com.example.demo.configuation;

import com.example.demo.Repository.inscriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class appConfig {
  private final inscriRepository inscriReposetory ;
  @Bean
    public UserDetailsService userDetailsService(){
      return username -> inscriReposetory.findByEml(username)//lambda expression to get the user by email
              .orElseThrow(()-> new UsernameNotFoundException("user not found"));
      }
    @Bean
    public AuthenticationProvider authenticationProvider(){//this is the custom authentication provider we created in the previous step to authenticate the user using DaoAuthenticationProvider
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setUserDetailsService(userDetailsService());
         provider.setPasswordEncoder(passwordEncoder());
         return provider;
    }
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception { //this is the authentication manager bean that will be used to authenticate the user
      return config.getAuthenticationManager();
    }
  @Bean
  public PasswordEncoder passwordEncoder() {
 return new BCryptPasswordEncoder();
  }

}





