package com.fran.gestionstarwars.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fran.gestionstarwars.entity.Films;

import com.fran.gestionstarwars.models.dao.IFilmsDAO;


@Service
public class FilmsServiceImpl implements IFilmsService{

	
	@Autowired
	private IFilmsDAO filmsDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Films> findAll() {
		return (List<Films>) filmsDAO.findAll();
	}

	@Override
	@Transactional
	public Films findById(int id) {
		return filmsDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Films save(Films film) {
		return filmsDAO.save(film);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		filmsDAO.deleteById(id);
	}

}
