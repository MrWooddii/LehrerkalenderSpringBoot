package com.lehrerkalender.email;

import com.lehrerkalender.grade.GradeRepository;
import com.lehrerkalender.student.StudentRepository;
import com.lehrerkalender.student.Student;
import com.lehrerkalender.student.StudentService;
import com.lehrerkalender.user.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

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
    void canSaveOrUpdateStudent() {
        Student student = new Student("firstName", "lastName");
        student.setUserId(10L);
        CustomUserDetails user = new CustomUserDetails();
        user.setId(10L);

        underTest.saveOrUpdateStudent(user, student);

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
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