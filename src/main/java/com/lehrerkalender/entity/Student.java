package com.lehrerkalender.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "Der Vorname darf nicht leer sein.")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Der Nachname darf nicht leer sein.")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_detail_id")
    private StudentDetail studentDetail;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Grade> gradeList = new ArrayList<>();

    @Column (name = "user_id")
    private Long userId;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}