package com.fran.gestionstarwars.model.services;

import java.util.List;

import com.fran.gestionstarwars.entity.Films;



public interface IFilmsService {
	
	public List<Films> findAll();
	
	public Films findById(int id);
	
	public Films save (Films film);
	
	public void delete (Integer id);
}
