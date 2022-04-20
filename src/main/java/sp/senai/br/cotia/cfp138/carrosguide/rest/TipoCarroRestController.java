package sp.senai.br.cotia.cfp138.carrosguide.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sp.senai.br.cotia.cfp138.carrosguide.annotation.Publico;
import sp.senai.br.cotia.cfp138.carrosguide.model.TipoCarro;
import sp.senai.br.cotia.cfp138.carrosguide.repository.TipoRepository;

@RequestMapping("/api/tipo")
@RestController
public class TipoCarroRestController {
	
	@Autowired
	private TipoRepository tipoRep;
	
	@Publico
	@RequestMapping(value="", method = RequestMethod.GET)
	public Iterable<TipoCarro> getCarros() {
		return tipoRep.findAll();
	}
	
	
}
