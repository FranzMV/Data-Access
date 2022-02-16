package com.fran.gestionstarwars.models.dto;

import java.io.Serializable;
import java.util.Set;



public class PeopleDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private int codigo;
	private int planets;
	private String name;
	private String birthYear;
	private String eyeColor;
	private String gender;
	private String hairColor;
	private String height;
	private String mass;
	private String skinColor;
	private String created;
	private String edited;
	
	private Set<Integer> starships;
	private Set<Integer> vehicles;
	private Set<Integer> species;
	private Set<Integer> films;
	
	public PeopleDTO() {
		super();
	}
	
	public PeopleDTO(int codigo) {
		this.codigo = codigo;
	}

	public PeopleDTO(int codigo, int planets, String name, String birthYear, String eyeColor, String gender,
			String hairColor, String height, String mass, String skinColor, String created, String edited,
			Set<Integer> starships, Set<Integer> vehicles, Set<Integer> species, Set<Integer> films) {
		super();
		this.codigo = codigo;
		this.planets = planets;
		this.name = name;
		this.birthYear = birthYear;
		this.eyeColor = eyeColor;
		this.gender = gender;
		this.hairColor = hairColor;
		this.height = height;
		this.mass = mass;
		this.skinColor = skinColor;
		this.created = created;
		this.edited = edited;
		this.starships = starships;
		this.vehicles = vehicles;
		this.species = species;
		this.films = films;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getPlanets() {
		return planets;
	}

	public void setPlanets(int planets) {
		this.planets = planets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHairColor() {
		return hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getMass() {
		return mass;
	}

	public void setMass(String mass) {
		this.mass = mass;
	}

	public String getSkinColor() {
		return skinColor;
	}

	public void setSkinColor(String skinColor) {
		this.skinColor = skinColor;
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

	public Set<Integer> getStarships() {
		return starships;
	}

	public void setStarshisps(Set<Integer> starships) {
		this.starships = starships;
	}

	public Set<Integer> getVehicles() {
		return vehicles;
	}

	public void setVehicles(Set<Integer> vehicles) {
		this.vehicles = vehicles;
	}

	public Set<Integer> getSpecies() {
		return species;
	}

	public void setSpecies(Set<Integer> species) {
		this.species = species;
	}

	public Set<Integer> getFilms() {
		return films;
	}

	public void setFilms(Set<Integer> films) {
		this.films = films;
	}
	
}
