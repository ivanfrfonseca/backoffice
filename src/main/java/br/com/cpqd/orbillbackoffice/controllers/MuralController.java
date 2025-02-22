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

import br.com.cpqd.orbillbackoffice.models.MuralModel;
import br.com.cpqd.orbillbackoffice.models.RespostaModel;
import br.com.cpqd.orbillbackoffice.services.MuralService;

@RestController
@CrossOrigin(origins = "*")
public class MuralController {
	
	@Autowired
	private MuralService muralService;
		
	@PostMapping("/cadastrarmural")
	public ResponseEntity<?> cadastrar(@RequestBody MuralModel sm ){
		return muralService.cadastrarEditar(sm, "cadastrar");
	}
	
	@PutMapping("/editarmural")
	public ResponseEntity<?> editar(@RequestBody MuralModel sm ){
		return muralService.cadastrarEditar(sm, "editar");
	}
	
	@DeleteMapping("/apagarmural/{id}")
	public ResponseEntity<RespostaModel>apagar(@PathVariable int id){
		return muralService.apagar(id);
	}

}
