package sp.senai.br.cotia.cfp138.carrosguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import sp.senai.br.cotia.cfp138.carrosguide.model.Carros;
import sp.senai.br.cotia.cfp138.carrosguide.model.TipoCarro;

public interface TipoRepository extends PagingAndSortingRepository<TipoCarro, Long> {
	
	@Query("SELECT t FROM TipoCarro t WHERE t.palavrasChave LIKE %:p%")
	public List<TipoCarro> procurarPorCampo(@Param("p") String palavrasChave);

	public List<TipoCarro> findAllByOrderByTipoAsc();
}
