package com.fran.gestionstarwars.model.services;

import java.util.List;

import com.fran.gestionstarwars.entity.Species;

public interface ISpeciesService {
	
	public List<Species> findAll();
	
	public Species findById(int id);
	
	public Species save (Species specie);
	
	public void delete (Integer id);
}
