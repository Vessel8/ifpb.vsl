package com.vslifb.showdomilhao.service;
import com.vslifb.showdomilhao.model.Course;
import com.vslifb.showdomilhao.repository.CourseRepository;
import org.hibernate.validator.internal.util.actions.LoadClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional( readOnly = true )
    public List<Course> findAll()
    {
        return courseRepository.findAll();
    }

    @Transactional
    public Course save( Course entity )
    {
        return courseRepository.save( entity );
    }

    @Transactional
    public Optional<Course> findByID (Long id )
    {
        return courseRepository.findById( id );
    }

    @Transactional
    public Optional<Course> updateCourse(Long id, String courseName, Integer durationHours, BigDecimal price, LocalDate releaseDate)
    {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            Course updatedCourse = existingCourse.get();
            updatedCourse.setCourseName(courseName);
            updatedCourse.setDurationHours(durationHours);
            updatedCourse.setPrice(price);
            updatedCourse.setReleaseDate(releaseDate);
            courseRepository.save(updatedCourse);
            return Optional.of(updatedCourse);
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteByID( Long id ) {
        courseRepository.deleteById( id );
    }
}
