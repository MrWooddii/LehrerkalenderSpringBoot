package com.lehrerkalender.student;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private List<Student> studentsList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        List<Student> students = List.of(new Student("student1", "student1"), new Student("abc", "abc"), new Student("student3", "student3"));
        for(Student student : students) {
            student.setUserId(10L);
            studentsList.add(student);

            System.out.println("student: " + student);
        }
        studentRepository.saveAll(students);
    }

    @AfterEach
    void tearDown() {
        studentsList.clear();
        studentRepository.deleteAll();
    }

    @Test
    @DisplayName("Liest SuS anhand Vor- oder Nachname sowie UserId aus")
    void findStudentsByNameAndUserId() {
        String nameToSearch = "tud";
        Long userId = 10L;

        List<Student> studentsToCheck = studentRepository.findStudentsByNameAndUserId(nameToSearch, userId);

        assertThat(studentsList.containsAll(studentsToCheck));
        assertThat(studentsToCheck).hasSize(2).extracting(Student::getUserId).containsOnly(10L);
    }

    @Test
    @DisplayName("Listet nur SuS auf, die zu einer bestimmten UserId gehören")
    void findStudentsByUserId() {
        Long userId = 10L;

        List<Student> studentsToCheck = studentRepository.findStudentsByUserId(userId);

        assertThat(studentsToCheck).hasSize(3).extracting(Student::getUserId).containsOnly(10L);
    }
}