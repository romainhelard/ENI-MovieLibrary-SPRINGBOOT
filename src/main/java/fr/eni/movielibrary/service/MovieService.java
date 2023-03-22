package fr.eni.movielibrary.service;

import java.util.List;

import fr.eni.movielibrary.bo.Genre;
import fr.eni.movielibrary.bo.Movie;
import fr.eni.movielibrary.bo.Opinion;
import fr.eni.movielibrary.bo.Participant;

public interface MovieService {
	List<Movie> getAllMovies();

	Movie getMovieById(long id);

	List<Genre> getGenres();

	Genre getGenreById(long id);

	List<Participant> getParticipants();

	Participant getParticipantById(long id);

	void saveMovie(Movie movie);

	// Pour gérer l'ajout et l'affichage des avis
	void saveOpinion(Opinion opinion, Movie selectedMovie);

	List<Opinion> getOpinionByMovie(long idMovie);
}
