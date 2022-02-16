package com.fran.gestionstarwars.model.services;

import java.util.List;

import com.fran.gestionstarwars.entity.Planets;

public interface IPlanetsService {

	public List<Planets> findAll();
	
	public Planets findById(int id);
	
	public Planets save (Planets planet);
	
	public void delete (Integer id);
}
