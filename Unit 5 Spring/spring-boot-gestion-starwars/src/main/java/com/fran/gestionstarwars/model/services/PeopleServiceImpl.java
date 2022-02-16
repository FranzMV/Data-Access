package com.fran.gestionstarwars.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fran.gestionstarwars.entity.People;
import com.fran.gestionstarwars.models.dao.*;

@Service
public class PeopleServiceImpl implements IPeopleService{

	@Autowired
	private IPeopleDAO peopleDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<People> findAll() {
		return (List<People>) peopleDAO.findAll();
	}

	
	@Override
	@Transactional
	public People findById(int id) {
		return peopleDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public People save(People people) {
		return peopleDAO.save(people);
		
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		peopleDAO.deleteById(id);
		
	}

}
