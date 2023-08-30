package com.example.demo.productEN;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data//lombok annotation to generate setters and getters
@Builder // to create builder class for our entity
@NoArgsConstructor // to create a no argument constructor for our entity class  (lombok annotation)
@AllArgsConstructor
@Entity
@Table(name = "payment_details")
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "payment_id")
    private String payId;
    @Column(name = "service_name")
    private String serviceN;
    @Column(name = "email")
    private String eml;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "price")
    private double price;
    @Column(name = "payment_method")
    private String paymentMethod;


}
