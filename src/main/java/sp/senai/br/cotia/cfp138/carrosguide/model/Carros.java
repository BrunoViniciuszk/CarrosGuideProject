package sp.senai.br.cotia.cfp138.carrosguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Carros {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private TipoCarro tipo;
	@Column(columnDefinition = "TEXT")
	private String especificacaoTec;
	private String marca;
	private String modelo;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	private String testeSeguranca;
	private boolean estepe;
	private boolean tetoSolar;
	private boolean alcaPqp;
	
	public String[] verFotos() {
		return this.fotos.split(";");
	}
}
