package com.lehrerkalender.grade;

import com.lehrerkalender.student.Student;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    @NotNull(message = "Bitte wähle ein Datum aus.")
    @PastOrPresent(message = "Das Datum darf nicht in der Zukunft liegen.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate date;

    @Column(name = "subject")
    @NotBlank(message = "Bitte wähle ein Thema aus.")
    String subject;

    @Column(name = "performance")
    @NotBlank(message = "Bitte gib eine Note ein.")
    @Range(min = 1, max = 6, message = "Bitte gib eine Note zwischen 1 und 6 ein.")
    String performance;

    @Column(name = "written")
    @NotNull(message = "Bitte wähle die Schriftform aus.")
    boolean written;

    @Column(name = "notes")
    String notes;

    @Column(name = "titel")
    @NotBlank(message = "Bitte gib den Titel des Tests ein.")
    String titel;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    Student student;

    public Grade(LocalDate date, String subject, String performance, boolean written, String notes, String titel) {
        this.date = date;
        this.subject = subject;
        this.performance = performance;
        this.written = written;
        this.notes = notes;
        this.titel = titel;
    }
}
