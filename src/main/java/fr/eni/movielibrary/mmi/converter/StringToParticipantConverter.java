package fr.eni.movielibrary.mmi.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.movielibrary.bo.Participant;
import fr.eni.movielibrary.service.MovieService;

@Component
public class StringToParticipantConverter implements Converter<String, Participant> {
	// Injection des services
	private MovieService service;

	@Autowired
	public StringToParticipantConverter(MovieService service) {
		this.service = service;
	}

	@Override
	public Participant convert(String id) {
		Long theId = Long.parseLong(id);
		return service.getParticipantById(theId);
	}
}
