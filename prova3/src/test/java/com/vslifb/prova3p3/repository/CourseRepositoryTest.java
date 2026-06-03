package com.vslifb.prova3p3.repository;

import com.vslifb.prova3p3.TestcontainersConfiguration;
import com.vslifb.prova3p3.model.Course;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test class for CourseRepository CRUD Operations with Testcontainers and sliced @DataJpaTest")
@DataJpaTest // Enables Spring Data JPA testing and rolls back transactions after each test
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Do not use an embedded database (H2, HSQLDB, Derby) if it's on the classpath
@Import({TestcontainersConfiguration.class})
@ActiveProfiles("test")
@Sql(scripts = "classpath:/sql/create-test-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/sql/drop-test-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CourseRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;


    // Test number 01
    @Test
    @DisplayName("Given a course, when saving, then course is persisted in the database")
    void givenCourse_whenSave_thenCourseIsPersisted() {
        Course course = aCourse();
        courseRepository.save(course);

        entityManager.clear(); // evict cache so the next read hits the database

        List<Course> courseList = findAllCourses();
        assertThat(courseList).hasSize(1);
        assertThat(courseList.getFirst().getCourseName())
                .as("Course name should be equal to 'Desenvolvimento de Aplicações Web I'")
                .isEqualTo("Desenvolvimento de Aplicações Web I");
        assertThat(courseList.getFirst().getDurationHours())
                .as("Duration hours should be 100")
                .isEqualTo(100);
        assertThat(courseList.getFirst().getReleaseDate())
                .as("Release date should be 1 month ahead of now")
                .isEqualTo(LocalDate.now().plusMonths(1));
        assertThat(courseList.getFirst().getPrice())
                .as("Course price should be equal to 50")
                .isEqualByComparingTo(BigDecimal.valueOf(50));
    }


    // Test number 02
    @Test
    @DisplayName("Given an existing course, when updating its data, then course data is changed in the database")
    void givenExistingCourse_whenUpdate_thenCourseIsUpdated() {
        persistCourse(aCourse());
        entityManager.clear(); // evict cache so we read the entity from the database

        Course course = findAllCourses().getFirst();
        course.setCourseName(anotherCourse().getCourseName());
        course.setDurationHours(anotherCourse().getDurationHours());
        course.setPrice(anotherCourse().getPrice());
        course.setReleaseDate(anotherCourse().getReleaseDate());

        courseRepository.saveAndFlush(course);
        entityManager.clear(); // evict cache so the next read hits the database

        List<Course> courseList = findAllCourses();
        assertThat(courseList).hasSize(1);
        assertThat(courseList.getFirst().getCourseName())
                .as("Should be Estrutura de Dados")
                .isEqualTo("Estrutura de Dados");
        assertThat(courseList.getFirst().getDurationHours())
                .as("Should be 67h")
                .isEqualTo(67);
        assertThat(courseList.getFirst().getReleaseDate())
                .as("Should be 2 months from now on")
                .isEqualTo(LocalDate.now().plusMonths(2));
        assertThat(courseList.getFirst().getPrice())
                .as("Should now be 40R$")
                .isEqualByComparingTo(BigDecimal.valueOf(40));
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

    private static Course anotherCourse() {
        Course course = new Course();
        course.setCourseName("Estrutura de Dados");
        course.setDurationHours(67);
        course.setPrice(BigDecimal.valueOf(40));
        course.setReleaseDate(LocalDate.now().plusMonths(2));
        return course;
    }

    private void persistCourse(Course course) {
        entityManager.persistAndFlush(course);
    }

    private List<Course> findAllCourses() {
        return entityManager.getEntityManager()
                .createQuery("SELECT c FROM Course c", Course.class)
                .getResultList();
    }
}
