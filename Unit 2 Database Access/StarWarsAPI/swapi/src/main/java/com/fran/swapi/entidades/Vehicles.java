package com.fran.swapi.entidades;

import com.google.gson.annotations.Expose;

/**
 * @author Francisco David Manzanedo Valle
 */

public class Vehicles {
	
	private int codigo;
	@Expose
	private String name;
	@Expose
	private String model;
	@Expose
	private String vehicle_class;
	@Expose
	private String manufacturer;
	@Expose
	private String length;
	@Expose
	private String cost_in_credits;
	@Expose
	private String crew;
	@Expose
	private String passengers;
	@Expose
	private String max_atmosphering_speed;
	@Expose
	private String cargo_capacity;
	@Expose
	private String consumables;
	@Expose
	private String created;
	@Expose
	private String edited;
	
	public Vehicles() { }
	
	
	public Vehicles(String name, String model, String vehicle_class, String manufacturer, String length,
			String cost_in_credits, String crew, String passengers, String max_atmosphering_speed, String cargo_capacity,
			String consumables, String created, String edited) {
		this.name = name;
		this.model = model;
		this.vehicle_class = vehicle_class;
		this.manufacturer = manufacturer;
		this.length = length;
		this.cost_in_credits = cost_in_credits;
		this.crew = crew;
		this.passengers = passengers;
		this.max_atmosphering_speed = max_atmosphering_speed;
		this.cargo_capacity = cargo_capacity;
		this.consumables = consumables;
		this.created = created;
		this.edited = edited;
	}
	
	
	public Vehicles(int codigo, String name, String model, String vehicle_class, String manufacturer, String length,
			String cost_in_credits, String crew, String passengers, String max_atmosphering_speed, String cargo_capacity,
			String consumables, String created, String edited) {
		super();
		this.codigo = codigo;
		this.name = name;
		this.model = model;
		this.vehicle_class = vehicle_class;
		this.manufacturer = manufacturer;
		this.length = length;
		this.cost_in_credits = cost_in_credits;
		this.crew = crew;
		this.passengers = passengers;
		this.max_atmosphering_speed = max_atmosphering_speed;
		this.cargo_capacity = cargo_capacity;
		this.consumables = consumables;
		this.created = created;
		this.edited = edited;
	}

	public int getCodigo() { return codigo; }

	public void setCodigo(int code) { this.codigo = code; }

	public String getName() { return name; }

	public void setName(String name) {this.name = name; }

	public String getModel() { return model; }

	public void setModel(String model) { this.model = model; }

	public String getVehicle_class() { return vehicle_class; }

	public void setVehicle_class(String vehicle_class) { this.vehicle_class = vehicle_class; }

	public String getManufacturer() { return manufacturer; }

	public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

	public String getLength() { return length; }

	public void setLength(String length) { this.length = length; }

	public String getCost_in_credits() { return cost_in_credits; }

	public void setCost_in_credits(String cost_in_credit) { this.cost_in_credits = cost_in_credit; }

	public String getCrew() { return crew; }

	public void setCrew(String crew) { this.crew = crew; }

	public String getPassengers() { return passengers; }

	public void setPassengers(String passengers) { this.passengers = passengers; }

	public String getMax_atmosphering_speed() { return max_atmosphering_speed; }

	public void setMax_atmosphering_speed(String max_atmosphering_speed) { this.max_atmosphering_speed = max_atmosphering_speed; }

	public String getCargo_capacity() { return cargo_capacity; }

	public void setCargo_capacity(String cargo_capacity) { this.cargo_capacity = cargo_capacity; }

	public String getConsumables() { return consumables; }

	public void setConsumables(String consumables) { this.consumables = consumables; }

	public String getCreated() { return created; }

	public void setCreated(String created) { this.created = created; }

	public String getEdited() { return edited; }

	public void setEdited(String edited) { this.edited = edited; }

	@Override
	public String toString() {
		return "Vehicles [codigo: " + codigo + ", name: " + name + ", model: " + model +
				", vehicle_class: " + vehicle_class+ ", manufacturer: " + manufacturer + 
				", length: " + length + ", cost_in_credits: " + cost_in_credits
				+ ", crew: " + crew + ", passengers: " + passengers + 
				", max_atmosphering_speed: " + max_atmosphering_speed+
				", cargo_capacity: " + cargo_capacity + ", consumables: " + consumables + 
				", created: " + created+ ", edited: " + edited + "]";
	}
}
