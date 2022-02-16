package com.fran.gestionstarwars.model.services;

import java.util.List;

import com.fran.gestionstarwars.entity.Vehicles;

public interface IVehiclesService {
	
	public List<Vehicles> findAll();
	
	public Vehicles findById(int id);
	
	public Vehicles save (Vehicles vehicle);
	
	public void delete (Integer id);
}
