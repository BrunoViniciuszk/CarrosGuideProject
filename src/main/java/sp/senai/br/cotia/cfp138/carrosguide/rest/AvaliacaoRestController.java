package sp.senai.br.cotia.cfp138.carrosguide.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sp.senai.br.cotia.cfp138.carrosguide.annotation.Privado;
import sp.senai.br.cotia.cfp138.carrosguide.annotation.Publico;
import sp.senai.br.cotia.cfp138.carrosguide.model.Avaliacao;
import sp.senai.br.cotia.cfp138.carrosguide.model.Carros;
import sp.senai.br.cotia.cfp138.carrosguide.repository.AvaliacaoRepository;

@RequestMapping("/api/avaliacao")
@RestController
public class AvaliacaoRestController {
	@Autowired
	private AvaliacaoRepository repository;
	
	@Privado
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao) {
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/avaliacao/"+avaliacao.getId())).body(avaliacao);
	}
	
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Avaliacao getAavaliacao(Long id) {
		return repository.findById(id).get();
	}

	
	@Publico
	@RequestMapping(value="/avaliacao/{idCarro}", method = RequestMethod.GET)
	public List<Avaliacao> findAvaliacaoCarro(@PathVariable("idCarro") Long idCarro) {
		// lista avaliacao por carro
		List<Avaliacao> avaliacao = repository.findByCarroId(idCarro);
		return avaliacao;
		 
	}
	
	
}
