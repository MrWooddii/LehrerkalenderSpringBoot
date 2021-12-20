package com.lehrerkalender.service;

import com.lehrerkalender.dao.GradeRepository;
import com.lehrerkalender.dao.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//Ersetzt Autoclosable
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    //muss nicht "autowired" werden, da Repository bereits getestet wurde -> Mock
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GradeRepository gradeRepository;

    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository, gradeRepository);
    }

    @Test
    void getStudents() {
        Long userId = 10L;

        underTest.getStudents(userId);

        verify(studentRepository).findStudentsByUserId(userId);
    }

    @Test
    @Disabled
    void getStudentById() {
    }

    @Test
    @Disabled
    void getStudentsByName() {
    }

    @Test
    @Disabled
    void saveOrUpdateStudent() {
    }

    @Test
    @Disabled
    void deleteStudent() {
    }

    @Test
    @Disabled
    void getGradesByStudent() {
    }

    @Test
    @Disabled
    void saveGrade() {
    }

    @Test
    @Disabled
    void deleteGrade() {
    }

    @Test
    @Disabled
    void getGradeById() {
    }
}