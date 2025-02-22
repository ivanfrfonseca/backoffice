package br.com.cpqd.orbillbackoffice.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cpqd.orbillbackoffice.models.RespostaModel;
import br.com.cpqd.orbillbackoffice.models.ScriptModel;
import br.com.cpqd.orbillbackoffice.repositories.ScriptRepository;

@Service
public class ScriptService {

	@Autowired
	private ScriptRepository scriptRep;
	
	@Autowired
	private RespostaModel respostaModel;
		
	//Pesquisa script por título e/ou categoria
	public List<ScriptModel> pesquisarScriptPorTitulo(String titulo, String categoria, int idEmpresa){
		List<ScriptModel> scripts = scriptRep.pesquisarScriptPorTitulo(titulo.toUpperCase(), idEmpresa);		
		List<ScriptModel> resultados = new ArrayList<>();
        for (ScriptModel script : scripts) {
            if(script.getCategoria().equals(categoria)) {
            	resultados.add(script);
            }
            else if (categoria.equals("Todos")) {
                resultados.add(script);
            }           	
        }
		return resultados;
	}
	
	//Cadastra ou edita Script
	public ResponseEntity<?> cadastrarEditar(ScriptModel sm, String acao){	
		
		// Essas validações estão sendo feitas em JS
		if(sm.getTitulo().equals("")) {
			respostaModel.setMensagem("O título do script é obrigatório");
			return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.BAD_REQUEST);
		} else if(sm.getCaminho().equals("")) {
			respostaModel.setMensagem("O caminho do script é obrigatório");
			return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.BAD_REQUEST);
		} else {
			
			if (acao.equals("cadastrar")) {
				return new ResponseEntity<ScriptModel>(scriptRep.save(sm), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<ScriptModel>(scriptRep.save(sm), HttpStatus.OK);
			}
			
		}
	}
	
	//Apaga Script
	public ResponseEntity<RespostaModel> apagar(long id){
		scriptRep.deleteById(id);
		respostaModel.setMensagem("Script apagado com sucesso!");
		return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.OK);
	}
	
	//Contar os scripts
	public String contarScripts(int idEmpresa) {
		return scriptRep.contarScripts(idEmpresa);
	}	
	
}
