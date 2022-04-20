package sp.senai.br.cotia.cfp138.carrosguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import sp.senai.br.cotia.cfp138.carrosguide.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
	
	
}
