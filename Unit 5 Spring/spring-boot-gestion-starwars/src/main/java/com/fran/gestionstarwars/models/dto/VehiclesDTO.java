package com.fran.gestionstarwars.models.dto;

import java.io.Serializable;
import java.util.Set;

public class VehiclesDTO implements Serializable {


	private static final long serialVersionUID = 1L;
	private int codigo;
	private String name;
	private String model;
	private String vehicleClass;
	private String manufacturer;
	private String length;
	private String costInCredits;
	private String crew;
	private String passengers;
	private String maxAtmospheringSpeed;
	private String cargoCapacity;
	private String consumables;
	private String created;
	private String edited;
	private Set<Integer> people;
	private Set<Integer> films;
	
	public VehiclesDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VehiclesDTO(Integer codigo) {
		this.codigo = codigo;
	}

	public VehiclesDTO(int codigo, String name, String model, String vehicleClass, String manufacturer, String length,
			String costInCredits, String crew, String passengers, String maxAtmospheringSpeed, String cargoCapacity,
			String consumables, String created, String edited, Set<Integer> people, Set<Integer> films) {
		super();
		this.codigo = codigo;
		this.name = name;
		this.model = model;
		this.vehicleClass = vehicleClass;
		this.manufacturer = manufacturer;
		this.length = length;
		this.costInCredits = costInCredits;
		this.crew = crew;
		this.passengers = passengers;
		this.maxAtmospheringSpeed = maxAtmospheringSpeed;
		this.cargoCapacity = cargoCapacity;
		this.consumables = consumables;
		this.created = created;
		this.edited = edited;
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getCostInCredits() {
		return costInCredits;
	}

	public void setCostInCredits(String costInCredits) {
		this.costInCredits = costInCredits;
	}

	public String getCrew() {
		return crew;
	}

	public void setCrew(String crew) {
		this.crew = crew;
	}

	public String getPassengers() {
		return passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}

	public String getMaxAtmospheringSpeed() {
		return maxAtmospheringSpeed;
	}

	public void setMaxAtmospheringSpeed(String maxAtmospheringSpeed) {
		this.maxAtmospheringSpeed = maxAtmospheringSpeed;
	}

	public String getCargoCapacity() {
		return cargoCapacity;
	}

	public void setCargoCapacity(String cargoCapacity) {
		this.cargoCapacity = cargoCapacity;
	}

	public String getConsumables() {
		return consumables;
	}

	public void setConsumables(String consumables) {
		this.consumables = consumables;
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
