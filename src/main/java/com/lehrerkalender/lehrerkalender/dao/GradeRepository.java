package com.lehrerkalender.lehrerkalender.dao;

import com.lehrerkalender.lehrerkalender.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    public List<Grade> findGradeByStudentId(Long studentId);

}
