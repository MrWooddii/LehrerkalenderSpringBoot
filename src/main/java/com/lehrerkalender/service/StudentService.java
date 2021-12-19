package com.lehrerkalender.service;

import com.lehrerkalender.dao.GradeRepository;
import com.lehrerkalender.dao.StudentRepository;
import com.lehrerkalender.entity.Grade;
import com.lehrerkalender.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final GradeRepository gradeRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if(studentOptional.isEmpty()) {
            return new Student();
        }

        Student student = studentOptional.get();

        return student;
    }

    //sucht nach den SuS nach dem eingegebenen Namen in der Suchfunktion
    //TODO: Tabelle dynamisch mit JS filtern lassen
    public List<Student> getStudentsByName(String name) {
        List<Student> students = studentRepository.findStudentsByName(name);
        return students;
    }

    public void saveOrUpdateStudent(Student student) {
        studentRepository.save(student);
        System.out.println("Saved student inside Service: " + student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Grade> getGradesByStudent(Long studentId) {
        return gradeRepository.findGradeByStudentId(studentId);
    }

    public void saveGrade(Grade grade) {
        gradeRepository.save(grade);
    }

    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    public Grade getGradeById(Long id) {
        Optional<Grade> gradeOptional = gradeRepository.findById(id);
        if(gradeOptional.isEmpty()) {
            return new Grade();
        }

        Grade grade = gradeOptional.get();

        return grade;
    }
}
