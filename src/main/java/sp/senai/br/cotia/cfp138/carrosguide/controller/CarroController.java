package sp.senai.br.cotia.cfp138.carrosguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import sp.senai.br.cotia.cfp138.carrosguide.model.Carros;
import sp.senai.br.cotia.cfp138.carrosguide.model.TipoCarro;
import sp.senai.br.cotia.cfp138.carrosguide.repository.CarroRepository;
import sp.senai.br.cotia.cfp138.carrosguide.repository.TipoRepository;

@Controller
public class CarroController {
	
	@Autowired
	private TipoRepository tipoRep;
	
	@Autowired
	private CarroRepository carroRep;
	
	
	@RequestMapping(value = "formCarro", method = RequestMethod.GET)
	public String formCarro(Model model) {
		model.addAttribute("tipos", tipoRep.findAllByOrderByTipoAsc());
		return "carros/formcarro";
	}
		
	
	@RequestMapping(value = "salvarCarro", method = RequestMethod.POST)
	public String salvarCarro(@Valid Carros carro) {
		carroRep.save(carro);
		return "redirect:listaCarro/1";
	}
	
	// request mapping para listar, informando a página desejada
		@RequestMapping("listaCarro/{page}")
		public String listar(Model model,@PathVariable("page") int page) {
			
			// cria um pageable com 6 elementos por página ordenando os objetos pelo nome de forma ascendente
			PageRequest pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.ASC, "modelo"));
			
			// cria a página atual através do repository
			Page<Carros> pagina = carroRep.findAll(pageable);
			
			// descobrir o total de páginas
			int totalPages = pagina.getTotalPages();
			
			// cria uma lista de inteiros para representar as páginas
			List<Integer> pageNumbers = new ArrayList<Integer>();
			
			// preencher a lista com as páginas
			for (int i = 0; i < totalPages; i++) {
				pageNumbers.add(i+1);
			}
			
			// adiciona as variáveis na model
			model.addAttribute("carros", pagina.getContent());
			model.addAttribute("paginaAtual", page);
			model.addAttribute("totalPaginas", totalPages);
			model.addAttribute("numPaginas", pageNumbers);
			
			// retorna para o html da lista
			return "carros/listacarro";
		}

	@RequestMapping("alterarCarro")
	public String alterarCarro(Model model, Long id) {
		Carros carro = carroRep.findById(id).get();
		model.addAttribute("carros", carro);
		return "forward:formCarro";
	}

	@RequestMapping("excluirCarro")
	public String excluirCarro(Long id) {
		carroRep.deleteById(id);
		return "redirect:listaCarro/1";
	}
	
	
}
