package com.lehrerkalender.lehrerkalender.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Lazy
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "road_name")
    private String roadName;

    @Column(name = "house_number")
    private String houseNumber;
}
