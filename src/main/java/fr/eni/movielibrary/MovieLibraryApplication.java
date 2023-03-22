package fr.eni.movielibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieLibraryApplication.class, args);
	}

//	public static void testBean(String[] args) {
//		ApplicationContext context = SpringApplication.run(MovieLibraryApplication.class, args);
//
//		MovieController movieBean = context.getBean("movieBean", MovieController.class);
//
//		// Affichage du premier film
////		System.out.println("Le premier film est : ");
////		Movie firstMovie = movieBean.findMovie(1);
////		System.out.println(firstMovie);
//
//		// Affichage de la liste des films
//		System.out.println("\nTous les films : ");
////		List<Movie> lstMovies = movieBean.showAllMovies();
////		for (Movie movie : lstMovies) {
////			System.out.println(movie);
////		}
//	}

}
