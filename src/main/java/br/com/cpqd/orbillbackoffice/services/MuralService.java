package br.com.cpqd.orbillbackoffice.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cpqd.orbillbackoffice.models.MuralModel;
import br.com.cpqd.orbillbackoffice.models.RespostaModel;
import br.com.cpqd.orbillbackoffice.repositories.MuralRepository;

@Service
public class MuralService {

	@Autowired
	private MuralRepository muralRep;
	
	@Autowired
	private RespostaModel respostaModel;
	
	//Lista as notas de atividades recorrentes do mural
	public List<MuralModel> listar1(int idEmpresa){		
		List<MuralModel> notas = muralRep.listarNotas1(idEmpresa);		
		List<MuralModel> resultados = new ArrayList<>();
        for (MuralModel nota : notas) {
            resultados.add(nota);
        } 		
		return resultados;
	}
	
	//Lista todas as notas de outras atividades do mural
	public List<MuralModel> listar2(int idEmpresa){		
		List<MuralModel> notas = muralRep.listarNotas2(idEmpresa);		
		List<MuralModel> resultados = new ArrayList<>();
        for (MuralModel nota : notas) {
            resultados.add(nota);
        } 		
		return resultados;
	}
	
	//Cadastra ou edita as notas do mural
	public ResponseEntity<?> cadastrarEditar(MuralModel mm, String acao){
		
		// Essas validações estão sendo feitas em JS
		if(mm.getTitulo().equals("")) {
			respostaModel.setMensagem("O título da nota é obrigatório");
			return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.BAD_REQUEST);
		} else if(mm.getTexto().equals("")) {
			respostaModel.setMensagem("O texto da nota é obrigatório");
			return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.BAD_REQUEST);
		} else {
			
			if(acao.equals("cadastrar")) {
				return new ResponseEntity<MuralModel>(muralRep.save(mm), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<MuralModel>(muralRep.save(mm), HttpStatus.OK);
			}
			
		}
	}
	
	//Apaga nota do mural
	public ResponseEntity<RespostaModel> apagar(long id){
		muralRep.deleteById(id);
		respostaModel.setMensagem("Nota apagada com sucesso!");
		return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.OK);
	}
	
	//Conta as notas do mural
	public String contarMural(int idEmpresa) {
		return muralRep.contarMural(idEmpresa);
	}
	
}
