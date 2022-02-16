package com.fran.gestionstarwars.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fran.gestionstarwars.entity.Starships;

public interface IStarshipDAO extends CrudRepository<Starships, Integer>{

}
