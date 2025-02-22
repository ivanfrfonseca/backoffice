package br.com.cpqd.orbillbackoffice.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import br.com.cpqd.orbillbackoffice.models.UsuarioModel;
import br.com.cpqd.orbillbackoffice.services.ArtigoService;
import br.com.cpqd.orbillbackoffice.services.MuralService;
import br.com.cpqd.orbillbackoffice.services.ScriptService;
//import br.com.cpqd.orbillbackoffice.services.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

	//@Autowired
	//private UsuarioService usuarioService;
	
	@Autowired
	private MuralService muralService;
	
	@Autowired
	private ScriptService scriptService;
	
	@Autowired
	private ArtigoService artigoService;
		
	@GetMapping("/")
	public String login() {
		return ("login");
	}
	
	@RequestMapping("home")
	public String logar(String usuario, String senha, String empresa, HttpSession session, Model model){		
		//UsuarioModel usuarioModel = usuarioService.logar(usuario, senha);
		String home = "";
		int idEmpresa = Integer.parseInt(empresa);						
		if(home == "") { //usurioModel != null
			//session.setAttribute("usuarioLogado", usuarioModel);
			model.addAttribute("contadorScripts", scriptService.contarScripts(idEmpresa));
			model.addAttribute("contadorArtigos", artigoService.contarArtigos(idEmpresa));
			model.addAttribute("contadorNotas", muralService.contarMural(idEmpresa));
			model.addAttribute("notas1", muralService.listar1(idEmpresa));
			model.addAttribute("notas2", muralService.listar2(idEmpresa));
			if(idEmpresa == 1) {
				home = "sto/home";
			} else if(idEmpresa == 2) {
				home = "conectcar/home";
			} else if(idEmpresa == 3) {
				home = "stp/home";
			}	
			return home;
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping("/logout")
	  public String logout(HttpSession session) {
	      //session.invalidate();
	      return "redirect:/";
	  }
	
}
