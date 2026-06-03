package com.vslifb.prova3p3.controller;

import com.vslifb.prova3p3.model.Course;
import com.vslifb.prova3p3.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping( "/" )
    public String index() {
        return "index";
    }

    @GetMapping( "/login" )
    public String login() {
        return "login";
    }

    @GetMapping( "/courses" )
    public String courses(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @ModelAttribute( "course" )
    private Course bindBookToHtmlForm()
    {
        return new Course(); // Initialize an empty Course "COMMAND OBJECT"
    }

    @GetMapping( "/courses/add")
    public String showAddForm() {
        return "add-course";
    }

    @PostMapping( "/courses/add" )
    public String addBook( @Valid @ModelAttribute Course course, BindingResult result )
    {
        if ( result.hasErrors() )
        {
            return "add-course";
        }
        courseService.save( course );
        return "redirect:/courses";
    }

    @GetMapping( "/courses/delete/{id}" )
    public String deleteCourse( @PathVariable Long id )
    {
        courseService.deleteByID( id );
        return "redirect:/courses";
    }

    @GetMapping( "/courses/edit/{id}" )
    public String showEditForm( @PathVariable( "id" ) Long id, Model model ) {
        Optional<Course> course = courseService.findByID( id );
        if ( course.isPresent() ) {
            model.addAttribute( "course", course.get() );
            return "edit-course";
        }
        return "redirect:/courses";
    }

    @PostMapping("/courses/edit/{id}")
    public String updateBook( @PathVariable( "id" ) Long id,
                             @Valid @ModelAttribute("course") Course course,
                             BindingResult result) {
        if ( result.hasErrors() ) {
            return "edit-course";
        }
        courseService.updateCourse( id, course.getCourseName(), course.getDurationHours(), course.getPrice(), course.getReleaseDate() );
        return "redirect:/courses";
    }
}
