package sp.senai.br.cotia.cfp138.carrosguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import sp.senai.br.cotia.cfp138.carrosguide.model.Carros;
import sp.senai.br.cotia.cfp138.carrosguide.model.TipoCarro;

public interface CarroRepository extends PagingAndSortingRepository<Carros, Long> {
	
	public List<Carros> findByTipoId(Long idTipo);
	
	public List<Carros> findByEstepe(boolean estepe);
	
	public List<Carros> findByModelo(String modelo);
}
