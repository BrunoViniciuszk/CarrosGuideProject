package sp.senai.br.cotia.cfp138.carrosguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import sp.senai.br.cotia.cfp138.carrosguide.util.HashUtil;

@Data
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(unique = true)
	private String email;
	private String senha;
	
	public void setSenha(String senha) {
		this.senha = HashUtil.hash256(senha);
	}
	
}
