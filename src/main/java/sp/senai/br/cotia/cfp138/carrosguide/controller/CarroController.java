package sp.senai.br.cotia.cfp138.carrosguide.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import sp.senai.br.cotia.cfp138.carrosguide.model.Carros;
import sp.senai.br.cotia.cfp138.carrosguide.model.TipoCarro;
import sp.senai.br.cotia.cfp138.carrosguide.repository.CarroRepository;
import sp.senai.br.cotia.cfp138.carrosguide.repository.TipoRepository;
import sp.senai.br.cotia.cfp138.carrosguide.util.FirebaseUtil;

@Controller
public class CarroController {
	
	@Autowired
	private TipoRepository tipoRep;
	
	@Autowired
	private CarroRepository carroRep;
	
	@Autowired
	private FirebaseUtil firebaseUtil;
	
	
	@RequestMapping(value = "formCarro", method = RequestMethod.GET)
	public String formCarro(Model model) {
		model.addAttribute("tipos", tipoRep.findAllByOrderByTipoAsc());
		return "carros/formcarro";
	}
		
	
	@RequestMapping(value = "salvarCarro", method = RequestMethod.POST)
	public String salvarCarro(@Valid Carros carro,@RequestParam("fileFotos") MultipartFile[] fileFotos) {
		// string para a url das fotos
		String fotos = carro.getFotos(); 
		// percorrer cada arquivo que foi submetido no formulario
		for(MultipartFile arquivo : fileFotos) {
			// verificar se o arquivo está vazio
			if(arquivo.getOriginalFilename().isEmpty()) {
				// vai para o proximo arquivo
				continue;
			}
			// faz o upload para a nuvem e obtém a url gerada
			try {
				fotos += firebaseUtil.uploadFile(arquivo)+";";
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		// atribui a String fotos ao objeto carro 
		carro.setFotos(fotos);
		carroRep.save(carro);
		return "redirect:formCarro";
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
		carro.verFotos();
		model.addAttribute("carros", carro);
		return "forward:formCarro";
	}

	@RequestMapping("excluirCarro")
	public String excluirCarro(Long id) {
		Carros carro = carroRep.findById(id).get();
		if(carro.getFotos().length() > 0) {
			for(String foto : carro.verFotos()) {
				firebaseUtil.deletar(foto);
			}
		}
		carroRep.delete(carro);
		return "redirect:listaCarro/1";
	}
	
	
	@RequestMapping("excluirFoto")
	public String excluirFoto(Long idCarro, int numFoto, Model model) {
		// busca o restaurante no banco de dados
		Carros carro = carroRep.findById(idCarro).get();
		// pegar a string da foto a ser excluída
		String fotoUrl = carro.verFotos()[numFoto];
		// excluir do firebase
		firebaseUtil.deletar(fotoUrl);
		// "arranca" a foto da String fotos
		carro.setFotos(carro.getFotos().replace(fotoUrl+";", ""));
		// salva no bd o objeto carro
		carroRep.save(carro);
		// adiciona o carro na Model
		model.addAttribute("carros", carro);
		// encaminhar para o form
		return "forward:formCarro";
	}
	
}
