package sp.senai.br.cotia.cfp138.carrosguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TipoCarro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipo;
	@Column(columnDefinition = "varchar(512)")
	private String descricao;
	private String palavrasChave; 
}
