package sp.senai.br.cotia.cfp138.carrosguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import sp.senai.br.cotia.cfp138.carrosguide.model.Avaliacao;
import sp.senai.br.cotia.cfp138.carrosguide.model.Carros;

public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long>{
	
	
	public List<Avaliacao> findByCarroId(Long idCarro);
}
