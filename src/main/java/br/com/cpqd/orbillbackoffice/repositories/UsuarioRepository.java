package br.com.cpqd.orbillbackoffice.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.cpqd.orbillbackoffice.models.UsuarioModel;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long>{	
	
	@Query(value="select * from usuario where usuario = :usuario and senha = :senha and ativo = '1'", nativeQuery=true)
	public UsuarioModel login(String usuario, String senha);

}
