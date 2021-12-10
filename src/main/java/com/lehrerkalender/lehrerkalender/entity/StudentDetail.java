package com.lehrerkalender.lehrerkalender.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@Entity
@Table(name = "student_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "emergency_number")
    private String emergencyNumber;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "religion")
    private String religion;

    public StudentDetail(String emergencyNumber, String email, Address address, String religion) {
        this.emergencyNumber = emergencyNumber;
        this.email = email;
        this.address = address;
        this.religion = religion;
    }
}
