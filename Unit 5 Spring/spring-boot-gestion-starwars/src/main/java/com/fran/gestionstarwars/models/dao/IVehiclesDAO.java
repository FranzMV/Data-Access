package com.fran.gestionstarwars.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fran.gestionstarwars.entity.Vehicles;

public interface IVehiclesDAO extends CrudRepository<Vehicles, Integer> {

}
