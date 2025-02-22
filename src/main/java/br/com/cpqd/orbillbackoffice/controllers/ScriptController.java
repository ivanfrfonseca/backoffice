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

import br.com.cpqd.orbillbackoffice.models.RespostaModel;
import br.com.cpqd.orbillbackoffice.models.ScriptModel;
import br.com.cpqd.orbillbackoffice.services.ScriptService;

@RestController
@CrossOrigin(origins = "*")
public class ScriptController {
	
	@Autowired
	private ScriptService scriptService;
		
	@PostMapping("/cadastrarscript")
	public ResponseEntity<?> cadastrar(@RequestBody ScriptModel sm ){
		return scriptService.cadastrarEditar(sm, "cadastrar");
	}
	
	@PutMapping("/editarscript")
	public ResponseEntity<?> editar(@RequestBody ScriptModel sm ){
		return scriptService.cadastrarEditar(sm, "editar");
	}
	
	@DeleteMapping("/apagarscript/{id}")
	public ResponseEntity<RespostaModel>apagar(@PathVariable int id){
		return scriptService.apagar(id);
	}
	
}
