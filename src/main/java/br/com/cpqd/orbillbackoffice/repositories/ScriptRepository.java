package br.com.cpqd.orbillbackoffice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cpqd.orbillbackoffice.models.ScriptModel;

@Repository
public interface ScriptRepository extends CrudRepository<ScriptModel, Long>{
	
	// Contador de Scripts
	@Query(value="select count(*) from script where fk_id_empresa = :empresa", nativeQuery=true)
	public String contarScripts(@Param("empresa") int idEmpresa);
	
	//Busca todos os scripts
	@Query(value="select * "
			   + "from script s "
			   + "where s.fk_id_empresa = :empresa "
			   + "and upper(s.titulo) like %:titulo% "			   
			   + "order by s.categoria desc, s.id desc", nativeQuery=true)
	public List<ScriptModel> pesquisarScriptPorTitulo(@Param("titulo") String titulo, @Param("empresa") int idEmpresa);
		
}
