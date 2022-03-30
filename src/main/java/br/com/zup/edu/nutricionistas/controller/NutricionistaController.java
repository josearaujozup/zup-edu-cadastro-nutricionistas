package br.com.zup.edu.nutricionistas.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.nutricionistas.model.Nutricionista;
import br.com.zup.edu.nutricionistas.repository.NutricionistaRepository;

@RestController
@RequestMapping("/nutricionistas")
public class NutricionistaController {
	
	private final NutricionistaRepository repository;
	
	public NutricionistaController(NutricionistaRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping
	ResponseEntity<Void> cadastrar(@RequestBody @Valid NutricionistaRequest request, UriComponentsBuilder uriComponentsBuilder){
		Nutricionista novoNutricionista = request.paraNutricionista();
		
		URI location = uriComponentsBuilder.path("/nutricionistas/{id}").buildAndExpand(novoNutricionista.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}
