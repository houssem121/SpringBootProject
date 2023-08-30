package com.example.demo.RequestResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Userinfo {
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private int numportable;

}
