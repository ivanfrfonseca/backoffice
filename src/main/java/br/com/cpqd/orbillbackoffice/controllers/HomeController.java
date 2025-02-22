package br.com.cpqd.orbillbackoffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cpqd.orbillbackoffice.services.ArtigoService;
import br.com.cpqd.orbillbackoffice.services.MuralService;
import br.com.cpqd.orbillbackoffice.services.ScriptService;

@Controller
public class HomeController {
	
	@Autowired
	private ArtigoService artigoService;
	
	@Autowired
	private ScriptService scriptService;
	
	@Autowired
	private MuralService muralService;

	
	// sto - Mostra a página inical, carrega os contadores e todas as notas
	@GetMapping("sto/home")
	public String stoHome(Model model) {		
		model.addAttribute("contadorScripts", scriptService.contarScripts(1));
		model.addAttribute("contadorArtigos", artigoService.contarArtigos(1));
		model.addAttribute("contadorNotas", muralService.contarMural(1));
		model.addAttribute("notas1", muralService.listar1(1));
		model.addAttribute("notas2", muralService.listar2(1));
		return ("sto/home");
	}
	
	// conectcar - Mostra a página inical, carrega os contadores e todas as notas
	@GetMapping("conectcar/home")
	public String conectcarHome(Model model) {		
		model.addAttribute("contadorScripts", scriptService.contarScripts(2));
		model.addAttribute("contadorArtigos", artigoService.contarArtigos(2));
		model.addAttribute("contadorNotas", muralService.contarMural(2));
		model.addAttribute("notas1", muralService.listar1(2));
		model.addAttribute("notas2", muralService.listar2(2));
		return ("conectcar/home");
	}
	
	// stp - Mostra a página inical, carrega os contadores e todas as notas
	@GetMapping("stp/home")
	public String stpHome(Model model) {		
		model.addAttribute("contadorScripts", scriptService.contarScripts(3));
		model.addAttribute("contadorArtigos", artigoService.contarArtigos(3));
		model.addAttribute("contadorNotas", muralService.contarMural(3));
		model.addAttribute("notas1", muralService.listar1(3));
		model.addAttribute("notas2", muralService.listar2(3));
		return ("stp/home");
	}
	
	// Pesquisa artigos por título
	@PostMapping("/pesquisarartigos")
    public String pesquisarArtigos(@RequestParam String titulo, @RequestParam String idEmpresa, Model model) {
		int empresa = Integer.parseInt(idEmpresa);
		String page = "";
		model.addAttribute("artigos", artigoService.pesquisarArtigoPorTitulo(titulo, empresa));
        if(empresa == 1) {
        	page = "sto/baseconhecimento";
        } else if(empresa == 2) {
        	page = "conectcar/baseconhecimento";
        } else if(empresa == 3) {
        	page = "stp/baseconhecimento";
        }
		return page; // Retorna para a página de pesquisa com os resultados
    }
	
	// Pesquisa scripts por título e/ou categoria
	@PostMapping("/pesquisarscripts")
    public String pesquisarScripts(@RequestParam String titulo, @RequestParam String categoria, @RequestParam int idEmpresa, Model model) {        
		String page = "";
		model.addAttribute("scripts", scriptService.pesquisarScriptPorTitulo(titulo, categoria, idEmpresa));
		if(idEmpresa == 1) {
        	page = "sto/scripts";
        } else if(idEmpresa == 2) {
        	page = "conectcar/scripts";
        } else if(idEmpresa == 3) {
        	page = "stp/scripts";
        }
		return page; // Retorna para a página de pesquisa com os resultados
    }
		
	// sto - Mostra artigo na tela para visualização
	@GetMapping("sto/artigo-{id}")
	public String stoMostrarArtigo(@PathVariable int id, Model model){					
		model.addAttribute("artigo", artigoService.pesquisarArtigoPorId(id)) ;
		return "sto/artigo";
	}
	
	// conectcar - Mostra artigo na tela para visualização
	@GetMapping("conectcar/artigo-{id}")
	public String conectcarMostrarArtigo(@PathVariable int id, Model model){					
		model.addAttribute("artigo", artigoService.pesquisarArtigoPorId(id)) ;
		return "conectcar/artigo";
	}
	
	// stp - Mostra artigo na tela para visualização
	@GetMapping("stp/artigo-{id}")
	public String stpMostrarArtigo(@PathVariable int id, Model model){					
		model.addAttribute("artigo", artigoService.pesquisarArtigoPorId(id)) ;
		return "stp/artigo";
	}
	
	// sto - Carrega artigo na tela para edição
	@GetMapping("sto/editarartigo-{id}")
	public String stoEditarArtigo(@PathVariable int id, Model model) {
		model.addAttribute("artigo", artigoService.pesquisarArtigoPorId(id));
	    return "sto/editarartigo";
	}
	
	// conectcar - Carrega artigo na tela para edição
	@GetMapping("conectcar/editarartigo-{id}")
	public String conectcarEditarArtigo(@PathVariable int id, Model model) {
		model.addAttribute("artigo", artigoService.pesquisarArtigoPorId(id));
	    return "conectcar/editarartigo";
	}
	
	// stp - Carrega artigo na tela para edição
	@GetMapping("stp/editarartigo-{id}")
	public String stpEditarArtigo(@PathVariable int id, Model model) {
		model.addAttribute("artigo", artigoService.pesquisarArtigoPorId(id));
	    return "stp/editarartigo";
	}
	
	
	// sto - Demais Mapeamentos 
	
	@GetMapping("sto/empresa")
	public String stoEmpresa() {
		return ("sto/empresa");
	}
	
	@GetMapping("sto/baseconhecimento")
	public String stoBaseConhecimento() {
		return ("sto/baseconhecimento");
	}
	
	@GetMapping("sto/cadastrarartigo")
	public String stoCadastrarArtigo() {
		return ("sto/cadastrarartigo");
	}
	
	@GetMapping("sto/scripts")
	public String stoScripts() {
		return ("sto/scripts");
	}
	
	@GetMapping("sto/faturamentos")
	public String stoFaturamentos() {
		return ("sto/faturamentos");
	}
	
	@GetMapping("sto/emails")
	public String stoEmails() {
		return ("sto/emails");
	}
		
	@GetMapping("sto/ambientes")
	public String stoAmbientes() {
		return ("sto/ambientes");
	}
	
	@GetMapping("sto/outsourcingchanges")
	public String stoOutsourcingChanges() {
		return ("sto/outsourcingchanges");
	}
	
	@GetMapping("sto/dicionario")
	public String stoDicionario() {
		return ("sto/dicionario");
	}
	
	
	// conectcar - Demais Mapeamentos
		
	@GetMapping("conectcar/empresa")
	public String conectcarEmpresa() {
		return ("conectcar/empresa");
	}
	
	@GetMapping("conectcar/integracoes")
	public String conectcarIntegracoes() {
		return ("conectcar/integracoes");
	}
	
	@GetMapping("conectcar/faturamentos")
	public String conectcarFaturamentos() {
		return ("conectcar/faturamentos");
	}
	
	@GetMapping("conectcar/baseconhecimento")
	public String conectcarBaseConhecimento() {
		return ("conectcar/baseconhecimento");
	}
	
	@GetMapping("conectcar/cadastrarartigo")
	public String conectcarCadastrarArtigo() {
		return ("conectcar/cadastrarartigo");
	}
	
	@GetMapping("conectcar/scripts")
	public String conectcarScripts() {
		return ("conectcar/scripts");
	}
	
	@GetMapping("conectcar/ambientes")
	public String conectcarAmbientes() {
		return ("conectcar/ambientes");
	}
	
	// stp - Demais Mapeamentos
	
	@GetMapping("stp/empresa")
	public String stpEmpresa() {
		return ("stp/empresa");
	}
	
	@GetMapping("stp/modelo")
	public String stpModelo() {
		return ("stp/modelo");
	}
	
	@GetMapping("stp/baseconhecimento")
	public String stpBaseConhecimento() {
		return ("stp/baseconhecimento");
	}
	
	@GetMapping("stp/cadastrarartigo")
	public String stpCadastrarArtigo() {
		return ("stp/cadastrarartigo");
	}
	
	@GetMapping("stp/scripts")
	public String stpScripts() {
		return ("stp/scripts");
	}
	
	@GetMapping("stp/ambientes")
	public String stpAmbientes() {
		return ("stp/ambientes");
	}
	
}
