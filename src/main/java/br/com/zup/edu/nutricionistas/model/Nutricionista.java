package br.com.zup.edu.nutricionistas.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Nutricionista {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
    private String email;

	@Column(nullable = false)
    private String CRN;

	@Column(nullable = false)
    private String cpf;

	@Column(nullable = false)
    private LocalDate dataNascimento;

	public Nutricionista(String nome, String email, String crn, String cpf, LocalDate dataNascimento) {
		this.nome = nome;
		this.email = email;
		CRN = crn;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}
	
	
	/**
	 * @deprecated construtor é de uso do hibernate
	 */
	@Deprecated
	public Nutricionista() {
		
	}
	
	public Long getId() {
		return id;
	}
}
