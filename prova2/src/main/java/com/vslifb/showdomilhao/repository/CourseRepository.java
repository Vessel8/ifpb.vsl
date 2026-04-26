package com.vslifb.showdomilhao.repository;

import com.vslifb.showdomilhao.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}