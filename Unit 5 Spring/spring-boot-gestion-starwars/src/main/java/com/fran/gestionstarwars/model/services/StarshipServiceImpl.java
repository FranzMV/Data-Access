package com.fran.gestionstarwars.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fran.gestionstarwars.entity.Starships;
import com.fran.gestionstarwars.models.dao.*;

@Service
public class StarshipServiceImpl implements IStarshipService{

	
	@Autowired
	private IStarshipDAO starshipDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Starships> findAll() {
		return (List<Starships>) starshipDAO.findAll();
	}

	@Override
	@Transactional
	public Starships findById(int id) {
		return starshipDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Starships save(Starships starships) {
		return starshipDAO.save(starships);
		
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		starshipDAO.deleteById(id);
	}

}
