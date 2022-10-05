package com.fran.gestionstarwars.functions;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.fran.gestionstarwars.entity.People;
import com.fran.gestionstarwars.entity.Planets;
import com.fran.gestionstarwars.entity.Species;
import com.fran.gestionstarwars.entity.Starships;
import com.fran.gestionstarwars.entity.Vehicles;
import com.fran.gestionstarwars.model.services.PeopleServiceImpl;
import com.fran.gestionstarwars.model.services.PlanetsServiceImpl;
import com.fran.gestionstarwars.model.services.SpeciesServiceImpl;
import com.fran.gestionstarwars.model.services.StarshipServiceImpl;
import com.fran.gestionstarwars.model.services.VehicleServiceImpl;

public class HelperFunctions {

	public static int getLastPeopleId(PeopleServiceImpl peopleServiceImpl) {
		List<People> peopleList = peopleServiceImpl.findAll();
		People people = peopleList.stream()
				.max(Comparator.comparing(People::getCodigo))
				.orElseThrow(NoSuchElementException::new);
		return people.getCodigo() +1;
	}
	
	
	public static int getLastStarshipsId(StarshipServiceImpl starshipServiceImpl) {
		List<Starships> starshipsList = starshipServiceImpl.findAll();
		Starships starship = starshipsList.stream()
				.max(Comparator.comparing(Starships::getCodigo))
				.orElseThrow(NoSuchElementException::new);
		return starship.getCodigo() + 1;
	}
	
	
	public static int getLastVechiclesId(VehicleServiceImpl vehicleServiceImpl) {
		List<Vehicles> vehiclesList = vehicleServiceImpl.findAll();
		Vehicles vehicle = vehiclesList.stream()
				.max(Comparator.comparing(Vehicles :: getCodigo))
				.orElseThrow(NoSuchElementException::new);
		return vehicle.getCodigo() + 1;
	}
	
	
	public static int getLastSpeciesId(SpeciesServiceImpl speciesServiceImpl) {
		List<Species> speciesList = speciesServiceImpl.findAll();
		Species specie = speciesList.stream()
					     .max(Comparator.comparing(Species :: getCodigo))
					     .orElseThrow(NoSuchElementException::new);
		
		return specie.getCodigo() +1;
	}
	
	
	public static int getLastPlanetsId(PlanetsServiceImpl planetsServiceImpl) {
		List<Planets> planetsList = planetsServiceImpl.findAll();
		Planets planet = planetsList.stream()
					    .max(Comparator.comparing(Planets :: getCodigo))
					    .orElseThrow(NoSuchElementException::new);
		
		return planet.getCodigo() + 1;
	}
	public static String getDate() {
	  Date date = new Date();  
      	  Timestamp ts= new Timestamp(date.getTime());  
          SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
      	  return String.valueOf(formatter.format(ts));
	}
}
