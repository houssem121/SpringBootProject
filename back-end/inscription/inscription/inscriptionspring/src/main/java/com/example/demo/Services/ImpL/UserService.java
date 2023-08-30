package com.example.demo.Services.ImpL;


import com.example.demo.Repository.inscriRepository;
import com.example.demo.inscriptionEntity.inscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserService implements com.example.demo.Services.interfaces.UserService {
    @Autowired
    public inscriRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
@Override
public inscription findByEmail(String email) {
    return repository.findByEml(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
}
    @Override
    public  boolean checkIfValidOldPassword(inscription user, String oldPassword) {
        return !passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public void changeUserPassword(inscription user, String password) {
        String uppassword = passwordEncoder.encode(password);
        user.setPwd(uppassword);

        repository.save(user);
    }
    @Override

    public inscription getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof UsernamePasswordAuthenticationToken auth)) {
            throw new AuthenticationCredentialsNotFoundException("User not authenticated");
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return findByEmail(userDetails.getUsername());
    }
}
