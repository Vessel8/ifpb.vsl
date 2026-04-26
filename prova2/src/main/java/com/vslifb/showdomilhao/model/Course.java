package com.vslifb.showdomilhao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "COURSE", schema = "db_courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")
    private Long id;


    @Size(max = 250)
    @NotNull
    @NotBlank(message = "Name required!")
    @Column(name = "COURSE_NAME", nullable = false, length = 250)
    private String courseName;


    @NotNull(message = "Duration required!")
    @Positive(message = "Invalid negative duration!")
    @Min(value = 40, message = "Minimum duration is 40h!")
    @Max(value = 300, message = "Maximum duration is 300h!")
    @Column(name = "DURATION_HOURS", nullable = false)
    private Integer durationHours;

    @NotNull(message = "Price required!")
    @Positive(message = "Invalid negative price!")
    @Min(value = 1, message = "Minimum price is R$1")
    @Max(value = 3000, message = "Maximum price is R$3000")
    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Date required!")
    @FutureOrPresent(message = "Invalid past date!")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // That's required so Thymeleaf doesn't invalidate the inserted data,
                                            // keeping it as "placeholder" in case any validation fails
                                            // That way, the user will be able to acknowledge their mistake
    @Column(name = "RELEASE_DATE", nullable = false)
    private LocalDate releaseDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

}