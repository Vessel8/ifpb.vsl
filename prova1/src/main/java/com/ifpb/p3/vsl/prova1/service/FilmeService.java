package com.ifpb.p3.vsl.prova1.service;

import com.ifpb.p3.vsl.prova1.model.Filme;
import com.ifpb.p3.vsl.prova1.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Transactional(readOnly = true)
    public List<Filme> findAll()
    {
        return filmeRepository.findAll();
    }

    @Transactional
    public void save(Filme entity)
    {
        filmeRepository.save(entity);
    }
}
