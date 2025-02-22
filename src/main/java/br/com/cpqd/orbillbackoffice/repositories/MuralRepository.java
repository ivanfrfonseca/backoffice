package br.com.cpqd.orbillbackoffice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cpqd.orbillbackoffice.models.MuralModel;

@Repository
public interface MuralRepository extends CrudRepository<MuralModel, Long>{
	
	@Query(value="select count(*) from mural where fk_id_empresa = :empresa", nativeQuery=true)
	public String contarMural(@Param("empresa") int idEmpresa);
	
	//Busca as notas tipo 'atividade recorrente' (1)
	@Query(value="select * from mural m where m.tipo = '1' and m.fk_id_empresa = :empresa order by cor", nativeQuery=true)
	public List<MuralModel> listarNotas1(@Param("empresa") int idEmpresa);
	
	//Busca as notas tipo 'outras atividades' (2)
	@Query(value="select * from mural m where m.tipo = '2' and m.fk_id_empresa = :empresa order by cor", nativeQuery=true)
	public List<MuralModel> listarNotas2(@Param("empresa") int idEmpresa);
	
}
