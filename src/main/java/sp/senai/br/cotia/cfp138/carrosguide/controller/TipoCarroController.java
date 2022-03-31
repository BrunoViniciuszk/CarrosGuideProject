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

import sp.senai.br.cotia.cfp138.carrosguide.model.TipoCarro;
import sp.senai.br.cotia.cfp138.carrosguide.repository.TipoRepository;



@Controller
public class TipoCarroController {
	@Autowired
	private TipoRepository tipoRep;

	// request mapping para o form
	@RequestMapping(value = "formTipo", method = RequestMethod.GET)
	public String formTipo() {
		return "tipoCarro/formtipo";
	}
	
	// request para salvar o adm
	@RequestMapping(value = "salvarTipo", method = RequestMethod.POST)
	public String salvarTipo(@Valid TipoCarro tipo, BindingResult result, RedirectAttributes att) {
		// verifica se houve erro na validação do objeto
		if(result.hasErrors()) {
			// envia mensagem de erro via requisicao
			att.addFlashAttribute("mensagemErro", "Verifique os campos...");
			return "redirect:formTipo";
		}
		try {
			// salva o administrador
			tipoRep.save(tipo);
			att.addFlashAttribute("mensagemSucesso", "Tipo de carro salvo com sucesso. ID:"+tipo.getId());
			return "redirect:formTipo";
		} catch (Exception e) {
			att.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o tipo do carro: "+e.getMessage());
		}
		return "redirect:formTipo/1";	
	}
	
	// request mapping para listar, informando a página desejada
		@RequestMapping("listaTipo/{totalElem}/{page}")
		public String listar(Model model,@PathVariable("page") int page, @PathVariable("totalElem") int totalElem) {
			
			// cria um pageable com 6 elementos por página ordenando os objetos pelo nome de forma ascendente
			PageRequest pageable = PageRequest.of(page-1, totalElem, Sort.by(Sort.Direction.ASC, "tipo"));
			
			// cria a página atual através do repository
			Page<TipoCarro> pagina = tipoRep.findAll(pageable);
			
			// descobrir o total de páginas
			int totalPages = pagina.getTotalPages();
			
			// cria uma lista de inteiros para representar as páginas
			List<Integer> pageNumbers = new ArrayList<Integer>();
			
			// preencher a lista com as páginas
			for (int i = 0; i < totalPages; i++) {
				pageNumbers.add(i+1);
			}
			
			// adiciona as variáveis na model
			model.addAttribute("tipos", pagina.getContent());
			model.addAttribute("paginaAtual", page);
			model.addAttribute("totalElemento", totalElem);
			model.addAttribute("totalPaginas", totalPages);
			model.addAttribute("numPaginas", pageNumbers);
			
			// retorna para o html da lista
			return "tipoCarro/listatipo";
		}

	@RequestMapping("alterarTipo")
	public String alterarTipo(Model model, Long id) {
		TipoCarro tipo = tipoRep.findById(id).get();
		model.addAttribute("tipos", tipo);
		return "forward:formTipo";
	}

	@RequestMapping("excluirTipo")
	public String excluirTipo(Long id) {
		tipoRep.deleteById(id);
		return "redirect:listaTipo/1";
	}
	
	@RequestMapping("buscaTipo")
	public String buscar() {
		return "tipoCarro/buscartipo";
	}
	
	@RequestMapping("buscarTipos")
	public String buscarTipos(String palavrasChave, Model model) {
		model.addAttribute("tipos", tipoRep.procurarPorCampo("%"+palavrasChave+"%"));
		return "tipoCarro/listatipo";
	}
	


}
