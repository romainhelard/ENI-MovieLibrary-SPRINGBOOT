package fr.eni.movielibrary.mmi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.movielibrary.bo.Genre;
import fr.eni.movielibrary.bo.Movie;
import fr.eni.movielibrary.bo.Opinion;
import fr.eni.movielibrary.bo.Participant;
import fr.eni.movielibrary.exception.BusinessException;
import fr.eni.movielibrary.service.MovieService;

@Controller("movieBean")
@RequestMapping("/movies")
@SessionAttributes({ "userSession", "movieParticipants", "movieGenres" })
public class MovieController {
	private MovieService movieService;

	@Autowired
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping
	public String showAllMovies(Model model) {
		List<Movie> lstMovies = movieService.getAllMovies();
		if (lstMovies == null) {
			lstMovies = new ArrayList<>();
		}
		model.addAttribute("movies", lstMovies);
		return "view-movies";
	}

	@GetMapping("/detail")
	public String findMovie(@RequestParam(name = "id") long id, Model model) {
		Movie current = movieService.getMovieById(id);
		List<Opinion> lst = movieService.getOpinionByMovie(id);
		current.setOpinions(lst);
		model.addAttribute("movie", current);
		return "view-movie-detail";
	}

	// Création d'un nouveau film
	@GetMapping("/create")
	public String createMovie(Model model) {
		// Test si un membre est connecté
		Object att = model.getAttribute("userSession");
		if (att != null) {
			Movie movie = new Movie();
			// Ajout de l'instance dans le modèle
			model.addAttribute("movie", movie);
			return "view-movie-form";
		} else {
			return "redirect:/login";
		}
	}

	// Création d'un nouveau film
	@PostMapping("/create")
	public String createMovie(@Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult, Model model) {
		// Test si un membre est connecté
		Object att = model.getAttribute("userSession");
		if (att != null) {
			if (bindingResult.hasErrors()) {// Etat de validation
				return "view-movie-form";
			} else {
				System.out.println(movie);
				try {
					movieService.saveMovie(movie);
					return "redirect:/movies";
				} catch (BusinessException e) {
					System.err.println(e.getErrors());
					model.addAttribute("movie", movie);
					model.addAttribute("be",e.getErrors());
					return "view-movie-form";
				}
			}
		} else {
			return "redirect:/login";
		}
	}

	// Injection en session des listes représentant les participants et les genres
	@ModelAttribute("movieParticipants")
	public List<Participant> getAllParticipants() {
		return movieService.getParticipants();
	}

	@ModelAttribute("movieGenres")
	public List<Genre> getAllGenres() {
		return movieService.getGenres();
	}
}
