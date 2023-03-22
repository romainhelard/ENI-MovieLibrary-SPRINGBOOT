package fr.eni.movielibrary.service;

import fr.eni.movielibrary.bo.Member;

public interface ConnectionService {
	Member login(String email, String password);
}
