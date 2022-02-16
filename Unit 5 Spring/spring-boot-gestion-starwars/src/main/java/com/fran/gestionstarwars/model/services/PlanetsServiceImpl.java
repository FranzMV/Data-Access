package com.fran.gestionstarwars.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fran.gestionstarwars.entity.Planets;
import com.fran.gestionstarwars.models.dao.*;

@Service
public class PlanetsServiceImpl implements IPlanetsService {

	@Autowired
	private IPlanetsDAO planetsDAO;
	
	@Override
	public List<Planets> findAll() {
		return (List<Planets>) planetsDAO.findAll();
	}

	@Override
	public Planets findById(int id) {
		return planetsDAO.findById(id).orElse(null);
	}

	@Override
	public Planets save(Planets planet) {
		return planetsDAO.save(planet);
	}

	@Override
	public void delete(Integer id) {
		planetsDAO.deleteById(id);
	}

}
