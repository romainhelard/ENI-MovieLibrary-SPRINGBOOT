package fr.eni.movielibrary.mmi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import fr.eni.movielibrary.bo.Member;
import fr.eni.movielibrary.service.ConnectionService;

@Controller
@SessionAttributes({ "userSession" })
public class ConnectionController {
	// Injection du service
	private ConnectionService connectionService;

	@Autowired
	public ConnectionController(ConnectionService connectionService) {
		this.connectionService = connectionService;
	}

	@GetMapping("/login")
	public String login() {
		return "view-login-form";
	}

	@PostMapping("/login")
	public String login(@RequestParam(required = true) String email, @RequestParam(required = true) String password,
			Model model) {
		Member user = connectionService.login(email, password);
		if (user != null) {
			model.addAttribute("userSession", user);
			return "redirect:/movies";//appel du contrôleur MovieController
		} else {
			return "view-login-form";
		}
	}

	@GetMapping("/logout")
	public String invalidateSession(SessionStatus status) {
		status.setComplete();
		// redirection
		return "redirect:/";
	}
}
