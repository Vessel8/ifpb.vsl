package com.vslifb.prova3p3.repository;

import com.vslifb.prova3p3.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}