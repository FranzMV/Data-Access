package com.fran.gestionstarwars.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fran.gestionstarwars.entity.Planets;

public interface IPlanetsDAO extends CrudRepository<Planets, Integer>{

}
