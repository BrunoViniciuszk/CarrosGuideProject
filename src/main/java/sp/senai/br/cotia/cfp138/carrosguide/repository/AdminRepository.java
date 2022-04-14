package sp.senai.br.cotia.cfp138.carrosguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import sp.senai.br.cotia.cfp138.carrosguide.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long> {
			
		public Administrador findByEmailAndSenha(String email, String senha);
}
