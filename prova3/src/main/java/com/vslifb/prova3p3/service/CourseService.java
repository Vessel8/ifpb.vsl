package com.vslifb.prova3p3.service;
import com.vslifb.prova3p3.model.Course;
import com.vslifb.prova3p3.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    // Had to remove Autowired for testing
    public CourseService(CourseRepository courseRepository )
    {
        this.courseRepository = courseRepository;
    }

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
            return Optional.of(updatedCourse);
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteByID( Long id ) {
        courseRepository.deleteById( id );
    }
}
