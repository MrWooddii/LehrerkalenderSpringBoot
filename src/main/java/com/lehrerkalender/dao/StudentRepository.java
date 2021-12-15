package com.lehrerkalender.dao;

import com.lehrerkalender.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    @Query("SELECT s FROM Student s WHERE lower(s.firstName) LIKE %:searchName% or lower(s.lastName) LIKE %:searchName%")
    public List<Student> findStudentsByName(@Param("searchName") String name);

    /*
    @Query("SELECT s FROM Student s WHERE s.id = :studentId")
    public Student getStudentById(@Param("studentId") Long id);
     */

}
