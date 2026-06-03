package com.vslifb.prova3p3.service;

import com.vslifb.prova3p3.model.Course;
import com.vslifb.prova3p3.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    private CourseService courseService;

    @BeforeEach
    void setUp() {
        courseService = new CourseService(courseRepository);
    }

    // Save (test number 01)

    @Test
    @DisplayName("save delegates to the repository and returns the persisted new course")
    void givenValidCourse_whenSave_thenDelegatesAndReturnsSavedCourse() {
        // System shall delegate course persistence to repository and return saved course
        Course newCourse = aCourse();
        given(courseRepository.save(newCourse)).willReturn(aSavedCourse());

        Course result = courseService.save(newCourse);

        assertThat(result.getId())
                .as("saved course should have a database-assigned PK ID")
                .isEqualTo(1L);
        assertThat(result.getCourseName())
                .as("saved course title should match the original input")
                .isEqualTo("Desenvolvimento de Aplicações Web I");
        assertThat(result.getDurationHours())
                .as("saved course duration should match the original input")
                .isEqualTo(100);
        assertThat(result.getReleaseDate())
                .as("saved course release date should match the original input")
                .isEqualTo(LocalDate.now().plusMonths(1));
        assertThat(result.getPrice())
                .as("saved course price should match the original input")
                .isEqualByComparingTo(BigDecimal.valueOf(50));
        then(courseRepository).should().save(newCourse);
        // Checking if saved() was called
    }

    // Update (test number 02)

    @Test
    @DisplayName("updateCourse mutates the managed entity's fields without calling save explicitly")
    void givenExistingCourse_whenUpdateCourse_thenAppliesNewFieldsToManagedEntity() {
        // System shall update a course fields without calling the save method
        // That means it will not save or overwrite a whole new entity, but edit an existing's parameters
        // using hibernate dirty-checking to persist
        Course managedCourse = aCourse();
        Course updateData = aCourseWithNewData();
        given(courseRepository.findById(1L)).willReturn(Optional.of(managedCourse));

        courseService.updateCourse(1L, updateData.getCourseName(), updateData.getDurationHours(), updateData.getPrice(), updateData.getReleaseDate());

        assertThat(managedCourse.getCourseName())
                .as("saved course title should match updated data")
                .isEqualTo("Estrutura de Dados");
        assertThat(managedCourse.getDurationHours())
                .as("saved course duration should match updated data")
                .isEqualTo(67);
        assertThat(managedCourse.getReleaseDate())
                .as("saved course release date should match match updated data")
                .isEqualTo(LocalDate.now().plusMonths(2));
        assertThat(managedCourse.getPrice())
                .as("saved course price should match the match updated data")
                .isEqualByComparingTo(BigDecimal.valueOf(40));
        then(courseRepository).should(never()).save(managedCourse);
        // Checking if save() hasn't been called
    }

    // Helpers

    private static Course aCourse() {
        Course course = new Course();
        course.setCourseName("Desenvolvimento de Aplicações Web I");
        course.setDurationHours(100);
        course.setPrice(BigDecimal.valueOf(50));
        course.setReleaseDate(LocalDate.now().plusMonths(1));
        return course;
    }

    private static Course aSavedCourse() {
        Course course = aCourse();
        course.setId(1L);
        return course;
    }

    private static Course aCourseWithNewData() {
        Course course = new Course();
        course.setCourseName("Estrutura de Dados");
        course.setDurationHours(67);
        course.setPrice(BigDecimal.valueOf(40));
        course.setReleaseDate(LocalDate.now().plusMonths(2));
        return course;
    }

}
