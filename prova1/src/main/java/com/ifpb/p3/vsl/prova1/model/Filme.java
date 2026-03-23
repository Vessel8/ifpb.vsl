package com.ifpb.p3.vsl.prova1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "filmes")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull // Se eu colocar messagem aqui, vai aparecer duplicada junto com a do NotEmpty
    @NotBlank(message = "Título em branco!")
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull(message = "Data em branco!")
    @Past(message = "Data inserida no futuro!")
    @Column(name = "data_lancamento", nullable = false)
    private LocalDate dataLancamento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

}