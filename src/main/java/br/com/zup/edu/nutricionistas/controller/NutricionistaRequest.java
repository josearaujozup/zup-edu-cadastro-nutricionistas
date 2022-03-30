package br.com.zup.edu.nutricionistas.controller;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zup.edu.nutricionistas.model.Nutricionista;

public class NutricionistaRequest {
	
    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String crn;

    @NotBlank
    @CPF
    private String cpf;
    
    @NotNull
    @Past
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    public NutricionistaRequest(String nome, String email, String crn, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.crn = crn;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }
    
    public NutricionistaRequest() {
    	
    }
    
    public Nutricionista paraNutricionista() {
    	return new Nutricionista(nome, email, crn, cpf, dataNascimento);
    }
    
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCrn() {
        return crn;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}
