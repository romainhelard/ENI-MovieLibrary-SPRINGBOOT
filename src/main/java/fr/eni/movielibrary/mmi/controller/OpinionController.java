package fr.eni.movielibrary.mmi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.movielibrary.bo.Member;
import fr.eni.movielibrary.bo.Movie;
import fr.eni.movielibrary.bo.Opinion;
import fr.eni.movielibrary.exception.BusinessException;
import fr.eni.movielibrary.service.MovieService;

@Controller
@RequestMapping("/opinions")
@SessionAttributes({ "userSession", "selectedMovie" })
public class OpinionController {
	private MovieService movieService;

	@Autowired
	public OpinionController(MovieService movieService) {
		this.movieService = movieService;
	}

	// Création d'un nouvel avis
	@GetMapping("/add")
	public String createOpinion(@RequestParam("id")long idMovie, Model model) {
		// Test si un membre est connecté
		Object att = model.getAttribute("userSession");
		if (att != null) {
			//Mise en session du film sélectionné
			model.addAttribute("selectedMovie", movieService.getMovieById(idMovie));
			Opinion opinion = new Opinion();
			// Ajout de l'instance dans le modèle
			model.addAttribute("opinion", opinion);

			return "view-opinion-form";
		} else {
			return "redirect:/login";
		}
	}

	// Récupération de l'objet opinion du formulaire
	// sauvegarde
	@PostMapping("/add")
	public String createOpinion(@ModelAttribute("opinion") Opinion opinion, Model model) {
		// Test si un membre est connecté
		Object att = model.getAttribute("userSession");
		if (att != null) {
			try {
				//Associé le membre à l'avis
				Member m =(Member) att;
				opinion.setMember(m);
				System.out.println(opinion);
				//Récupération du film en session :
				Movie selectedMovie = (Movie) model.getAttribute("selectedMovie");
				movieService.saveOpinion(opinion, selectedMovie);
				return "redirect:/movies";
			} catch (BusinessException e) {
				System.err.println(e.getErrors());
				return "view-opinion-form";
			}
		} else {
			return "redirect:/login";
		}
	}
}
