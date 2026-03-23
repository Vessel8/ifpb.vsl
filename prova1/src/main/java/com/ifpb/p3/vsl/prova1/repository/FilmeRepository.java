package com.ifpb.p3.vsl.prova1.repository;

import com.ifpb.p3.vsl.prova1.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Integer> {
}