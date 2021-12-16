package com.lehrerkalender.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @NotBlank(message = "Bitte gib einen Benutzernamen an.")
    private String username;

    @Column(name = "password")
    @Size(min = 4, message = "Das Passwort muss mindestens 4 Zeichen lang sein.")
    private String password;

    @Column(name = "active")
    private boolean active;

    @Column(name = "role")
    private String roles;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Bitte gib eine E-Mail an.")
    private String email;

}
