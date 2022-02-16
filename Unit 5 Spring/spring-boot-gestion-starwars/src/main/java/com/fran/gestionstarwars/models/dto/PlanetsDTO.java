package com.fran.gestionstarwars.models.dto;

import java.io.Serializable;
import java.util.Set;

public class PlanetsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private int codigo;
	private String name;
	private String diameter;
	private String rotationPeriod;
	private String orbitalPeriod;
	private String gravity;
	private String population;
	private String climate;
	private String terrain;
	private String surfaceWater;
	private String created;
	private String edited;
	
	private Set<Integer> species;
	private Set<Integer> people;
	private Set<Integer> films;
	
	public PlanetsDTO() {
		super();
		
	}
	
	public PlanetsDTO(int codigo) {
		this.codigo = codigo;
	}

	public PlanetsDTO(int codigo, String name, String diameter, String rotationPeriod, String orbitalPeriod,
			String gravity, String population, String climate, String terrain, String surfaceWater, String created,
			String edited, Set<Integer> species, Set<Integer> people, Set<Integer> films) {
		super();
		this.codigo = codigo;
		this.name = name;
		this.diameter = diameter;
		this.rotationPeriod = rotationPeriod;
		this.orbitalPeriod = orbitalPeriod;
		this.gravity = gravity;
		this.population = population;
		this.climate = climate;
		this.terrain = terrain;
		this.surfaceWater = surfaceWater;
		this.created = created;
		this.edited = edited;
		this.species = species;
		this.people = people;
		this.films = films;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public String getRotationPeriod() {
		return rotationPeriod;
	}

	public void setRotationPeriod(String rotationPeriod) {
		this.rotationPeriod = rotationPeriod;
	}

	public String getOrbitalPeriod() {
		return orbitalPeriod;
	}

	public void setOrbitalPeriod(String orbitalPeriod) {
		this.orbitalPeriod = orbitalPeriod;
	}

	public String getGravity() {
		return gravity;
	}

	public void setGravity(String gravity) {
		this.gravity = gravity;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public String getSurfaceWater() {
		return surfaceWater;
	}

	public void setSurfaceWater(String surfaceWater) {
		this.surfaceWater = surfaceWater;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getEdited() {
		return edited;
	}

	public void setEdited(String edited) {
		this.edited = edited;
	}

	public Set<Integer> getSpecies() {
		return species;
	}

	public void setSpecies(Set<Integer> species) {
		this.species = species;
	}

	public Set<Integer> getPeople() {
		return people;
	}

	public void setPeople(Set<Integer> people) {
		this.people = people;
	}

	public Set<Integer> getFilms() {
		return films;
	}

	public void setFilms(Set<Integer> films) {
		this.films = films;
	}
	
	
	
	
	

}
