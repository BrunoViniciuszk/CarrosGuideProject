package sp.senai.br.cotia.cfp138.carrosguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import sp.senai.br.cotia.cfp138.carrosguide.util.HashUtil;
// para gerar o get e o set
@Data
// para mapear como uma identidade JPA
@Entity
public class Administrador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;
	
	// metodo para "setar" a senha aplicando hash
	public void setSenha(String senha) {
		// aplica o hash e "seta" a senha no objeto
		this.senha = HashUtil.hash256(senha);
	}
	
	// metodo para aplicar a senha sem aplicar o hash
	public void setSenhaComHash(String hash) {
		// "seta" o hash na senha
		this.senha = hash;
	}
	
	
	
}
