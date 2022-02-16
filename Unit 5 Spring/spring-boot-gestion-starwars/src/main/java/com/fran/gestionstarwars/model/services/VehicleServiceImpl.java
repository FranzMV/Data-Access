package com.fran.gestionstarwars.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fran.gestionstarwars.entity.Vehicles;
import com.fran.gestionstarwars.models.dao.*;


@Service
public class VehicleServiceImpl implements IVehiclesService{
	
	@Autowired
	private IVehiclesDAO vehiclesDAO;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Vehicles> findAll(){
		return (List<Vehicles>) vehiclesDAO.findAll();
	}


	@Override
	@Transactional
	public Vehicles findById(int id) {
		return vehiclesDAO.findById(id).orElse(null);
	}


	@Override
	@Transactional
	public Vehicles save(Vehicles vehicle) {
		return vehiclesDAO.save(vehicle);
	}


	@Override
	public void delete(Integer id) {
		vehiclesDAO.deleteById(id);
	}
	

}
