package com.vslifb.prova3p3.integration;

import com.vslifb.prova3p3.TestcontainersConfiguration;
import com.vslifb.prova3p3.model.Course;
import com.vslifb.prova3p3.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@DisplayName("Test class for CourseController endpoints")
@AutoConfigureMockMvc
@Import({ TestcontainersConfiguration.class }) // Wires in the MySQL Testcontainer as a datasource
@ActiveProfiles("test")
@Transactional // Ensures each test rolls back after running (isolated tests)
@Sql(scripts = "classpath:/sql/create-test-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) // Prepare schema/data
@Sql(scripts = "classpath:/sql/drop-test-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)  // Clean up schema/data
public class CourseControllerTest {
    @Autowired
    private MockMvcTester mockMvc; // AssertJ-based entry point; auto-registered by @AutoConfigureMockMvc

    @Autowired
    private CourseRepository courseRepository;


    // Test number 01
    @Test
    // @WithMockUser: Simulates an authenticated user for security checks without hitting a real auth backend.
    @WithMockUser(username = "fulaninho", password = "senhanadasecreta", roles = { "ADMIN" }) // Simulate authenticated user
    @DisplayName("POST /courses/add - Should persist course and redirect")
    void givenAuthenticatedUser_whenPostCourse_thenItIsSavedAndRedirects() {

        var response = mockMvc.perform(
                post("/courses/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // Mimics an HTML form submission
                        .param("courseName", "Administração de Sistemas")
                        .param("durationHours", Integer.toString(67))
                        .param("price", BigDecimal.valueOf(60).toString())
                        .param("releaseDate", LocalDate.now().plusMonths(3).toString())
                        .with(csrf()) // Include CSRF token (required when CSRF protection is enabled)
        );

        // Assert: HTTP layer — controller redirects after successful creation.
        // Both assertions use AssertJ, the same library used below for business assertions.
        assertThat(response)
                .as("Expected a 302 redirect after creating a course.")
                .hasStatus(HttpStatus.FOUND);

        assertThat(response)
                .redirectedUrl()
                .as("Expected redirect to /courses after creation.")
                .isEqualTo("/courses");

        // Assert: verify the course was persisted in the database
        assertThat(courseRepository.count())
                .as("Expected exactly one course to be saved.")
                .isEqualTo(1);

        Course course = courseRepository.findAll().getFirst();
        assertThat(course.getCourseName())
                .as("Expected the saved course to be 'Administração de Sistemas'")
                .isEqualTo("Administração de Sistemas");

        assertThat(course.getDurationHours())
                .as("Expected the saved course to have 67h.")
                .isEqualTo(67);

        assertThat(course.getPrice())
                .as("Expected the saved course to cost 60R$.")
                .isEqualByComparingTo(BigDecimal.valueOf(60));

        assertThat(course.getReleaseDate())
                .as("Expected the saved course to release from 3 months now on")
                .isEqualTo(LocalDate.now().plusMonths(3));
    }


    // Test number 02
    @Test
    @WithMockUser(username = "fulaninho", password = "senhanadasecreta", roles = { "ADMIN" }) // Simulate authenticated user
    @DisplayName("POST /courses/edit/{id} - Should update course data and redirect")
    void givenAuthenticatedUserAndExistingCourse_whenEdit_thenItIsUpdatedAndRedirects(){
        // Arrange: create and persist a course to be edited in this test
        Course course = aCourse();
        Course saved = persistCourse( course ); // Persist to DB and get the generated ID

        // Act: submit a form-like POST request to update the course's data.
        // perform() returns MvcTestResult — no checked exception, no "throws Exception" needed.
        var response = mockMvc.perform(
                post("/courses/edit/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // Mimics an HTML form submission
                        .param("courseName", "Administração de Sistemas")
                        .param("durationHours", Integer.toString(67))
                        .param("price", BigDecimal.valueOf(60).toString())
                        .param("releaseDate", LocalDate.now().plusMonths(3).toString())
                        .with(csrf()) // Include CSRF token (required when CSRF protection is enabled)
        );

        // Assert: HTTP layer — controller redirects after successful update.
        // Both assertions use AssertJ, the same library used below for business assertions.
        assertThat(response)
                .as("Expected a 302 redirect after update.")
                .hasStatus(HttpStatus.FOUND);

        assertThat(response)
                .redirectedUrl()
                .as("Expected redirect to /courses after update.")
                .isEqualTo("/courses");

        // Assert: verify the course's data was updated in the database
        assertThat(courseRepository.count())
                .as("Expected exactly one course to be updated")
                .isEqualTo(1);

        course = courseRepository.findAll().getFirst();
        assertThat(course.getCourseName())
                .as("Expected the saved course to be 'Administração de Sistemas'")
                .isEqualTo("Administração de Sistemas");

        assertThat(course.getDurationHours())
                .as("Expected the saved course to have 67h.")
                .isEqualTo(67);

        assertThat(course.getPrice())
                .as("Expected the saved course to cost 60R$.")
                .isEqualByComparingTo(BigDecimal.valueOf(60));

        assertThat(course.getReleaseDate())
                .as("Expected the saved course to release from 3 months now on")
                .isEqualTo(LocalDate.now().plusMonths(3));
    }

    // Helpers
    private Course persistCourse(Course course ) {
        return courseRepository.saveAndFlush( course );
    }

    private static Course aCourse() {
        Course course = new Course();
        course.setCourseName("Desenvolvimento de Aplicações Web I");
        course.setDurationHours(100);
        course.setPrice(BigDecimal.valueOf(50));
        course.setReleaseDate(LocalDate.now().plusMonths(1));
        return course;
    }
}
