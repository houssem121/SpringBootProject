package com.example.demo.inscriptionEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Data//lombok annotation to generate setters and getters
@Builder // to create builder class for our entity
@NoArgsConstructor // to create a no argument constructor for our entity class  (lombok annotation)
@AllArgsConstructor // to create a constructor with all arguments for our entity class (lombok annotation)
@Entity // to make our class as an entity class (JPA annotation)
@Table(name = "inscriptiontabel")   // to specify the table name if different from class name (JPA annotation)
public class inscription implements UserDetails {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private  Long id;
 @Column( name = "name")
 private  String nn;
 @Column( name = "prenom")
 private  String pnm;
 @Column( name = "email")
 private String eml;
    @Column( name = "adresse")
 private  String adrs;
    @Column( name = "numportable")
 private  int nmp;
@Column( name = "password")
 private  String pwd;



    @Override
    public String toString() {
        return "inscription{" +
                "id=" + id +
                ", nom='" + nn + '\'' +
                ", prenom='" + pnm + '\'' +
                ", email='" + eml + '\'' +
                ", adresse='" + adrs + '\'' +
                ", numportable=" + nmp +
                ", password='" + pwd + '\'' +
                '}';
    }


    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {

        return pwd;
    }

    @Override
    public String getUsername() {
        return eml;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
