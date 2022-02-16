package com.fran.gestionstarwars.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fran.gestionstarwars.entity.Species;

public interface ISpeciesDAO extends CrudRepository<Species, Integer>{

}
