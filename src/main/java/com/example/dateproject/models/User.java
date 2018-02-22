package com.example.dateproject.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Email(message = "Please provide a valid email")
    @NotEmpty(message = "Please provide an email")
    private String email;

    @NotEmpty(message = "Please enter a password")
    @Transient
    private String password;

    private String name;

    private int active;

}
