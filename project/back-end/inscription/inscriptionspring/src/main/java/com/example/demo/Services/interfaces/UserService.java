package com.example.demo.Services.interfaces;

import com.example.demo.inscriptionEntity.inscription;

public interface UserService {

    public abstract inscription findByEmail(String email);
    public  abstract boolean checkIfValidOldPassword(inscription inscription, String oldPassword);
    public  abstract void  changeUserPassword(inscription inscription, String password);
    public  abstract  inscription getCurrentAuthenticatedUser();
}
