package br.com.cpqd.orbillbackoffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cpqd.orbillbackoffice.models.ArtigoModel;
import br.com.cpqd.orbillbackoffice.models.RespostaModel;
import br.com.cpqd.orbillbackoffice.services.ArtigoService;

@RestController
@CrossOrigin(origins = "*")
public class ArtigoController {
	
	@Autowired
	private ArtigoService artigoService;
			
	@PostMapping("/cadastrarartigo")
	public ResponseEntity<?> cadastrar(@RequestBody ArtigoModel am ){
		return artigoService.cadastrarEditar(am, "cadastrar");
	}
	
	@PutMapping("/editarartigo")
	public ResponseEntity<?> editar(@RequestBody ArtigoModel am ){
		return artigoService.cadastrarEditar(am, "editar");
	}
	
	@DeleteMapping("/apagarartigo/{id}")
	public ResponseEntity<RespostaModel>apagar(@PathVariable int id){
		return artigoService.apagar(id);
	}
			
}
