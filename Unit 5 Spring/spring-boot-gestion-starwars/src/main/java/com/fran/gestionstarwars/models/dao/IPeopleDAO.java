package com.fran.gestionstarwars.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fran.gestionstarwars.entity.People;

public interface IPeopleDAO extends CrudRepository<People, Integer>{

}
