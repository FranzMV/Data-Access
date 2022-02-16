package com.fran.gestionstarwars.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fran.gestionstarwars.entity.Films;

public interface IFilmsDAO extends CrudRepository<Films, Integer>{

}
