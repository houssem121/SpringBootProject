package com.example.demo.Services.interfaces;

import com.example.demo.Execeptions.EmailLreadyExistsException;
import com.example.demo.RequestResponse.AuthenticationRequest;
import com.example.demo.RequestResponse.AuthenticationResponse;
import com.example.demo.RequestResponse.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface InscriptionService {
  public abstract AuthenticationResponse authenticate(AuthenticationRequest request);
  public abstract AuthenticationResponse register(RegisterRequest request) throws EmailLreadyExistsException;
    public abstract AuthenticationResponse Googlelogin(AuthenticationResponse request) throws IOException;
    public abstract ResponseEntity<?> createPayment() throws IOException;
    public abstract ResponseEntity<?> verifyPayment(String id) throws IOException;
}
