package br.com.cpqd.orbillbackoffice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cpqd.orbillbackoffice.models.ArtigoModel;

@Repository
public interface ArtigoRepository extends CrudRepository<ArtigoModel, Long>{
	
	//Contador de artigos
	@Query(value="select count(*) from artigo where fk_id_empresa = :empresa", nativeQuery=true)
	public String contarArtigos(@Param("empresa") int idEmpresa);

	//Busca artigo por título
	@Query(value="select * "
			   + "from artigo a "
			   + "where a.fk_id_empresa = :empresa "
			   + "and upper(a.titulo) like %:titulo% "
			   + "order by TO_DATE(a.data, 'DD/MM/YYYY') desc", nativeQuery=true)
	public List<ArtigoModel> pesquisarArtigoPorTitulo(@Param("titulo") String titulo, @Param("empresa") int idEmpresa);
	
	//Busca artigo por ID
	@Query(value="select * from artigo a where a.id = :id", nativeQuery=true)
	public ArtigoModel pesquisarArtigoPorId(@Param("id") int id);
}
