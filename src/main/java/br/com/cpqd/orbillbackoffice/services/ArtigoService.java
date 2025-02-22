package br.com.cpqd.orbillbackoffice.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cpqd.orbillbackoffice.models.ArtigoModel;
import br.com.cpqd.orbillbackoffice.models.RespostaModel;
import br.com.cpqd.orbillbackoffice.repositories.ArtigoRepository;

@Service
public class ArtigoService {

	@Autowired
	private ArtigoRepository artigoRep;
	
	@Autowired
	private RespostaModel respostaModel;
		
	//Busca artigo da base de conhecimento por ID
	public ArtigoModel pesquisarArtigoPorId(int id){
		return artigoRep.pesquisarArtigoPorId(id);
	}
	
	//Pesquisa artigos por título
	public List<ArtigoModel> pesquisarArtigoPorTitulo(String titulo, int idEmpresa){
		List<ArtigoModel> artigos = artigoRep.pesquisarArtigoPorTitulo(titulo.toUpperCase(), idEmpresa);		
		List<ArtigoModel> resultados = new ArrayList<>();
        for (ArtigoModel artigo : artigos) {
        	resultados.add(artigo);
        }	
		return resultados;
	}
		
	//Cadastra ou edita um artigo da base de conhecimento
	public ResponseEntity<?> cadastrarEditar(ArtigoModel am, String acao){		
		
		// Essas validações estão sendo feitas em JS
		if(am.getTitulo().equals("")) {
			respostaModel.setMensagem("O título do artigo é obrigatório");
			return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.BAD_REQUEST);
		} else if(am.getTexto().equals("")) {
			respostaModel.setMensagem("O texto do artigo é obrigatório");
			return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.BAD_REQUEST);
		} else {
			
			if (acao.equals("cadastrar")) {
				return new ResponseEntity<ArtigoModel>(artigoRep.save(am), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<ArtigoModel>(artigoRep.save(am), HttpStatus.OK);
			}
			
		}
	}
	
	//Apaga artigo
	public ResponseEntity<RespostaModel> apagar(long id){
		artigoRep.deleteById(id);
		respostaModel.setMensagem("Artigo apagado com sucesso!");
		return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.OK);
	}
	
	//Contador de artigos
	public String contarArtigos(int idEmpresa) {
		return artigoRep.contarArtigos(idEmpresa);
	}
}
