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

import sp.senai.br.cotia.cfp138.carrosguide.model.Administrador;
import sp.senai.br.cotia.cfp138.carrosguide.repository.AdminRepository;
import sp.senai.br.cotia.cfp138.carrosguide.util.HashUtil;

@Controller
public class AdmController {
	// repository para persistencia do adm
	//autowiredpara injetar a dependencia
	@Autowired
	private AdminRepository admRep;
	
	// request mapping para o form
	@RequestMapping(value = "formAdm", method = RequestMethod.GET)
	public String formAdmin() {
		return "adm/formadm";
	}
	// request para salvar o adm
	@RequestMapping(value = "salvarAdm", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador adm, BindingResult result, RedirectAttributes att) {
		// verifica se houve erro na validação do objeto
		if(result.hasErrors()) {
			// envia mensagem de erro via requisicao
			att.addFlashAttribute("mensagemErro", "Verifique os campos...");
			return "redirect:formAdm";
		}
		boolean alteracao = adm.getId() != null ? true : false;
		
		if(adm.getSenha().equals(HashUtil.hash256(""))) {
			if(!alteracao) {
				String parte = adm.getEmail().substring(0, adm.getEmail().indexOf("@"));
				adm.setSenha(parte);
			}else {
				// busca a senha atual
				String hash = admRep.findById(adm.getId()).get().getSenha();
				// "seta" a senha com hash
				adm.setSenhaComHash(hash);
			}
		}
		
		try {
			// salva o administrador
			admRep.save(adm);
			att.addFlashAttribute("mensagemSucesso", "Administrador salvo com sucesso. Caso a senha não tenha sido informada no cadastro, será a parte do e-mail. ID:"+adm.getId());
			return "redirect:formAdm";
		} catch (Exception e) {
			att.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Administrador: "+e.getMessage());
		}
		return "redirect:formAdm";	
	}
	
	// request mapping para listar, informando a página desejada
	@RequestMapping("listaAdm/{numElement}/{page}")
	public String listar(Model model,@PathVariable("page") int page) {
		
		// cria um pageable com 6 elementos por página ordenando os objetos pelo nome de forma ascendente
		PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		
		// cria a página atual através do repository
		Page<Administrador> pagina = admRep.findAll(pageable);
		
		// descobrir o total de páginas
		int totalPages = pagina.getTotalPages();
		
		// cria uma lista de inteiros para representar as páginas
		List<Integer> pageNumbers = new ArrayList<Integer>();
		
		// preencher a lista com as páginas
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i+1);
		}
		
		// adiciona as variáveis na model
		model.addAttribute("admins", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		
		// retorna para o html da lista
		return "adm/lista";
	}
	
	@RequestMapping("alterarAdm") 
	public String alterarAdm(Model model, Long id) {
		Administrador admin = admRep.findById(id).get();
		model.addAttribute("admins", admin);
		return "forward:formAdm";
	}
	
	@RequestMapping("excluirAdm")
	public String excluirAdm(Long id) {
		admRep.deleteById(id);
		return "redirect:listaAdm/1";
	}
	
}
