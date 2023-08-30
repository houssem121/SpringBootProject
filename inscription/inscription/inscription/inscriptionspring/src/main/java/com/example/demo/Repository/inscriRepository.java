package com.example.demo.Repository;


import com.example.demo.inscriptionEntity.inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface inscriRepository extends JpaRepository<inscription,Long>{


    Optional<inscription> findByEml(String email);

    boolean existsByEml(String email);
}
