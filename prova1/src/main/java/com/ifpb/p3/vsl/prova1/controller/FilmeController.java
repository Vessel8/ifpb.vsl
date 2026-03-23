package com.ifpb.p3.vsl.prova1.controller;

import com.ifpb.p3.vsl.prova1.model.Filme;
import com.ifpb.p3.vsl.prova1.repository.FilmeRepository;
import com.ifpb.p3.vsl.prova1.service.FilmeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class FilmeController {

    private final FilmeService filmeService;
    private final FilmeRepository filmeRepository;

    public FilmeController(FilmeService filmeService, FilmeRepository filmeRepository) {
        this.filmeService = filmeService;
        this.filmeRepository = filmeRepository;
    }


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/filmes")
    public String filmes(Model model) {
        List<Filme> filmeList = filmeRepository.findAll();
        model.addAttribute("filmes", filmeList);

        return "listagem";
    }

    @GetMapping("/filmes/novo")
    public String addFilme(){
        return "add_filme";
    }

    @ModelAttribute("filme")
    private Filme filmeModel()
    {
        return new Filme();
    }

    @PostMapping( "/filmes/novo" )
    public String addBook(@Valid @ModelAttribute("filme") Filme filme, BindingResult result)
    {
        if (result.hasErrors())
        {
            return "add_filme";
        }
        filmeService.save(filme);
        return "redirect:/filmes";
    }
}
