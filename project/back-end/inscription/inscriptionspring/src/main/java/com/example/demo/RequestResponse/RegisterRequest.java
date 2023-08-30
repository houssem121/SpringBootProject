package com.example.demo.RequestResponse;

import com.example.demo.inscriptionEntity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {




        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private  String address;
        private  int portable;
        private Role role=Role.USER;

}