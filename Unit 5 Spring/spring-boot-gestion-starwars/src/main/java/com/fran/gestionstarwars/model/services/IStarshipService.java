package com.fran.gestionstarwars.model.services;

import java.util.List;

import com.fran.gestionstarwars.entity.Starships;

public interface IStarshipService {

	public List<Starships> findAll();
		
	public Starships findById(int id);
	
	public Starships save (Starships starships);
	
	public void delete (Integer id);
}
