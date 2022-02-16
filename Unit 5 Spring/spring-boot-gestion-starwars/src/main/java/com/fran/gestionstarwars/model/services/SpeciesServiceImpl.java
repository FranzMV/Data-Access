package com.fran.gestionstarwars.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fran.gestionstarwars.entity.Species;
import com.fran.gestionstarwars.models.dao.*;

@Service
public class SpeciesServiceImpl  implements ISpeciesService{

	@Autowired
	private ISpeciesDAO speciesDAO;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Species> findAll() {
		return (List<Species>) speciesDAO.findAll();
	}

	@Override
	@Transactional
	public Species findById(int id) {
		return speciesDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Species save(Species specie) {
		return speciesDAO.save(specie);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		speciesDAO.deleteById(id);
	}

}
