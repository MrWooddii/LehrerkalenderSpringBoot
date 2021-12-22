package com.lehrerkalender.controller;

import com.lehrerkalender.grade.Grade;
import com.lehrerkalender.student.Student;
import com.lehrerkalender.student.StudentService;
import com.lehrerkalender.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final String redirectToClassOverviewLink = "redirect:/students/class-overview";

    @Autowired
    private StudentService studentService;

    @RequestMapping("/class-overview")
    public String showClassOverview(Model model, HttpSession session, @AuthenticationPrincipal CustomUserDetails user) {

        session.removeAttribute("studentToGrade");

        List<Student> students = studentService.getStudents(user.getId());

        model.addAttribute("students", students);

        return "class-overview";
    }

    @GetMapping("/search")
    public String searchStudent(@RequestParam("searchName") String searchName, @AuthenticationPrincipal CustomUserDetails user, Model model) {

        System.out.println("searchName: " + searchName);

        List<Student> students = studentService.getStudentsByName(searchName, user.getId());

        //TODO: Fehlermeldung ausgeben
        if (students.isEmpty()) {
            System.out.println("students is empty");
            return redirectToClassOverviewLink;
        }

        model.addAttribute("students", students);
        return "class-overview";
    }

    @GetMapping("/showFormAddStudents")
    public String showFormAddStudents(Model model) {

        Student student = new Student();

        model.addAttribute("student", student);
        return "students-form";
    }

    @PostMapping("/saveOrUpdate")
    @Transactional
    public String saveOrUpdateStudent(@Valid @ModelAttribute("student") Student student, BindingResult result, @AuthenticationPrincipal CustomUserDetails user) {

        if(result.hasErrors()) {
            return "students-form";
        }

        studentService.saveOrUpdateStudent(user, student);

        return redirectToClassOverviewLink;
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("studentId") Long id, Model model) {

        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);

        return "students-form";
    }

    @GetMapping("/deleteStudent")
    public String deleteStudent(@RequestParam("studentId") Long id) {

        if(id == null) {
            System.out.println("testi");
            return redirectToClassOverviewLink;
        }
        studentService.deleteStudent(id);
        return redirectToClassOverviewLink;
    }

    @GetMapping("/showStudentDetails")
    public String showStudentDetails(@RequestParam("studentId") Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute(student);
        return "student-details-overview";
    }

    @GetMapping("/studentPerformance")
    public String showStudentPerformance(@RequestParam("studentId") Long id, Model model, HttpSession session) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        session.setAttribute("studentToGrade", student);
        List<Grade> grades = studentService.getGradesByStudent(id);
        model.addAttribute("grades", grades);

        return "student-performance";
    }

    @GetMapping("/saveGrade")
    public String showFormAddGrade(Model model, HttpSession session) {

        Student student = (Student) session.getAttribute("studentToGrade");

        Grade grade = new Grade();
        grade.setStudent(student);

        model.addAttribute("grade", grade);
        model.addAttribute("student", student);
        return "grade-add-form";
    }

    @PostMapping("/saveGrade")
    public String saveGrade(@Valid @ModelAttribute("grade") Grade grade, BindingResult result, Model model, HttpSession session) {

        Student student = (Student) session.getAttribute("studentToGrade");

        model.addAttribute("student", student);

        if(result.hasErrors()) {
            System.out.println(">>>>>>>>>>Grade-Form has errors");
            return "grade-add-form";
        }

        grade.setStudent(student);
        studentService.saveGrade(grade);
        List<Grade> grades = studentService.getGradesByStudent(student.getId());
        model.addAttribute("grades", grades);
        return "student-performance";
        //return redirectToClassOverviewLink;
    }

    @GetMapping("/updateGrade")
    public String updateGrade(@RequestParam("gradeId") Long gradeId, Model model){
        Grade gradeToUpdate = studentService.getGradeById(gradeId);
        Student student = studentService.getStudentById(gradeToUpdate.getStudent().getId());

        model.addAttribute("student", student);
        model.addAttribute("grade", gradeToUpdate);
        return "grade-add-form";
    }

    @GetMapping("/deleteGrade")
    public String deleteGrade(@RequestParam("gradeId") Long gradeId, Model model) {
        Grade gradeToDelete = studentService.getGradeById(gradeId);
        Long studentId = gradeToDelete.getStudent().getId();
        Student student = studentService.getStudentById(studentId);

        studentService.deleteGrade(gradeId);

        List<Grade> grades = studentService.getGradesByStudent(studentId);
        model.addAttribute("student", student);
        model.addAttribute("grades", grades);

        return "student-performance";
    }


}
