package com.revature.library.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name="messages")
public class User {
    @Id
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

//    //TODO check if integer is a proper datatype for phone numbers
//    @Column
//    private int phoneNumber;
//
//    @Column
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
//    private Date dob;

    //TODO generate constructors and getters/setters
}
