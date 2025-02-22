package br.com.cpqd.orbillbackoffice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cpqd.orbillbackoffice.models.UsuarioModel;
import br.com.cpqd.orbillbackoffice.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRep;	
	
	//Método para logar
	public UsuarioModel logar(String usuario, String senha){
		return usuarioRep.login(usuario, senha);
	}
	
}
