package fr.eni.movielibrary.mmi.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.movielibrary.bo.Genre;
import fr.eni.movielibrary.service.MovieService;

@Component
public class StringToGenreConverter implements Converter<String, Genre> {
	// Injection des services
	private MovieService service;

	@Autowired
	public StringToGenreConverter(MovieService service) {
		this.service = service;
	}

	@Override
	public Genre convert(String id) {
		Long theId = Long.parseLong(id);
		return service.getGenreById(theId);
	}
}
