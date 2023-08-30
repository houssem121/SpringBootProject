package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Services.ImpL.inscriptionServices;
import java.io.IOException;
@RestController
@RequestMapping(path = "/payment")
public class PaymentController {
    @Autowired
    private inscriptionServices inscriptionServices;



    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment() throws IOException {
        return ResponseEntity.ok(inscriptionServices.createPayment());
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestBody String id) throws IOException {

        return ResponseEntity.ok(inscriptionServices.verifyPayment(id));
    }
}
