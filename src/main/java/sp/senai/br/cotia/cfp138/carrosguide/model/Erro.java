package sp.senai.br.cotia.cfp138.carrosguide.model;

import lombok.Data;

@Data
public class Erro {
	private int statusCode;
	private String mensagem;
	private String exception;
}
