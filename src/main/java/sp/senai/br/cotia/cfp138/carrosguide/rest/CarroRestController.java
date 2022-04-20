package sp.senai.br.cotia.cfp138.carrosguide.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sp.senai.br.cotia.cfp138.carrosguide.annotation.Publico;
import sp.senai.br.cotia.cfp138.carrosguide.model.Carros;
import sp.senai.br.cotia.cfp138.carrosguide.repository.CarroRepository;


@RequestMapping("/api/carro")
@RestController
public class CarroRestController {
	
	@Autowired
	private CarroRepository repository;
	
	
	@Publico
	@RequestMapping(value="", method = RequestMethod.GET)
	public Iterable<Carros> getCarros() {
		return repository.findAll();
	}
	
	@Publico
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Carros> findCarro(@PathVariable("id") Long idCarro) {
		// busca o carro
		 Optional<Carros> carro = repository.findById(idCarro);
		 if(carro.isPresent()) {
			 return ResponseEntity.ok(carro.get());
		 }else {
			 return ResponseEntity.notFound().build();
		 }
	}
	
	@Publico
	@RequestMapping(value="/tipo/{idTipo}", method = RequestMethod.GET)
	public List<Carros> findTipoCarro(@PathVariable("idTipo") Long idTipo) {
		// busca o tipo do carro
		return repository.findByTipoId(idTipo);
		 
	}
	
	@Publico
	@RequestMapping(value="/estepe/{estepe}", method = RequestMethod.GET)
	public List<Carros> findEstepe(@PathVariable("estepe") boolean estepe) {
		return repository.findByEstepe(estepe);
	}
	
	@Publico
	@RequestMapping(value="/modelo/{modelo}", method = RequestMethod.GET)
	public List<Carros> findModelo(@PathVariable("modelo") String modelo) {
		return repository.findByModelo(modelo);
	}
	

}
