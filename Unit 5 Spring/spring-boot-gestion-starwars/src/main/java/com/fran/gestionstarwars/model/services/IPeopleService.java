package com.fran.gestionstarwars.model.services;

import com.fran.gestionstarwars.entity.People;

import java.util.List;

public interface IPeopleService {
	
	public List<People> findAll();
	
	
	public People findById(int id);
	
	public People save (People people);
	
	public void delete (Integer id);
}
