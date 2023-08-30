package com.example.demo.Controller;

import com.example.demo.Execeptions.InvalidOldPasswordException;
import com.example.demo.RequestResponse.PasswordUpdateRequest;
import com.example.demo.RequestResponse.Userinfo;
import com.example.demo.Services.ImpL.UserService;
import com.example.demo.Services.ImpL.inscriptionServices;
import com.example.demo.inscriptionEntity.inscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v2/user")


public class UserController {
    @Autowired
    public inscriptionServices inscriptionServices;
    @Autowired
    public   UserService userservice;

    @PutMapping("/updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestBody PasswordUpdateRequest request) {
        inscription currentUser = userservice.getCurrentAuthenticatedUser();

        if (userservice.checkIfValidOldPassword(currentUser, request.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }

        userservice.changeUserPassword(currentUser, request.getNewPassword());

        String response = "Password updated successfully.";
        return ResponseEntity.ok(response);
    }



    @GetMapping(path="/userinfo")
    public ResponseEntity<Userinfo> updateinfo() {
        inscription currentUser = userservice.getCurrentAuthenticatedUser();

        Userinfo userDTO = new Userinfo();
        userDTO.setNom(currentUser.getNn());
        userDTO.setPrenom(currentUser.getPnm());
        userDTO.setEmail(currentUser.getEml());
        userDTO.setAdresse(currentUser.getAdrs());
        userDTO.setNumportable(currentUser.getNmp());

        return ResponseEntity.ok(userDTO);
    }
}

