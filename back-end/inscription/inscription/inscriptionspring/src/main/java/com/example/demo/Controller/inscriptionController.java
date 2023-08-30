package com.example.demo.Controller;


import com.example.demo.Repository.PaymentDetailsRepository;
import com.example.demo.RequestResponse.AuthenticationRequest;
import com.example.demo.RequestResponse.AuthenticationResponse;
import com.example.demo.RequestResponse.RegisterRequest;
import com.example.demo.Services.ImpL.inscriptionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
//@RequiredArgsConstructor
@RequestMapping(path = "api/v2/inscription")
public class inscriptionController {
    @Autowired
    private inscriptionServices inscriptionServices;
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

        return ResponseEntity.ok(inscriptionServices.register(request));


    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(inscriptionServices.authenticate(request));
    }

    @PostMapping("/Googlelogin")
    public ResponseEntity<AuthenticationResponse> Googlelogin(@RequestBody AuthenticationResponse request) throws IOException {
        return ResponseEntity.ok(inscriptionServices.Googlelogin(request));
    }





}




