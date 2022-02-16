package com.fran.swapi.entidades;

import com.google.gson.annotations.Expose;

/**
 * @author Francisco David Manzanedo Valle
 *
 */

public class Starships {

	
	private int codigo;
	@Expose
	private String name;
	@Expose
	private String model;
	@Expose
	private String starship_class;
	@Expose
	private String manufacturer;
	@Expose
	private String cost_in_credits;
	@Expose
	private String length;
	@Expose
	private String crew;
	@Expose
	private String passengers;
	@Expose
	private String max_atmosphering_speed;
	@Expose
	private String hyperdrive_rating;
	@Expose
	private String MGLT;
	@Expose
	private String cargo_capacity;
	@Expose
	private String consumables;
	@Expose
	private String created;
	@Expose
	private String edited;
	
	
	public Starships() { }
	
	
	public Starships(String name, String model, String starship_class, String manufacturer,
			String cost_in_credits, String length, String crew, String passengers, String max_atmosphering_speed,
			String hyperdrive_rating, String mGLT, String cargo_capacity, String consumables, String created,
			String edited) {
		this.name = name;
		this.model = model;
		this.starship_class = starship_class;
		this.manufacturer = manufacturer;
		this.cost_in_credits = cost_in_credits;
		this.length = length;
		this.crew = crew;
		this.passengers = passengers;
		this.max_atmosphering_speed = max_atmosphering_speed;
		this.hyperdrive_rating = hyperdrive_rating;
		MGLT = mGLT;
		this.cargo_capacity = cargo_capacity;
		this.consumables = consumables;
		this.created = created;
		this.edited = edited;
	}
	
	
	public Starships(int codigo, String name, String model, String starship_class, String manufacturer,
			String cost_in_credits, String length, String crew, String passengers, String max_atmosphering_speed,
			String hyperdrive_rating, String mGLT, String cargo_capacity, String consumables, String created,
			String edited) {
		super();
		this.codigo = codigo;
		this.name = name;
		this.model = model;
		this.starship_class = starship_class;
		this.manufacturer = manufacturer;
		this.cost_in_credits = cost_in_credits;
		this.length = length;
		this.crew = crew;
		this.passengers = passengers;
		this.max_atmosphering_speed = max_atmosphering_speed;
		this.hyperdrive_rating = hyperdrive_rating;
		MGLT = mGLT;
		this.cargo_capacity = cargo_capacity;
		this.consumables = consumables;
		this.created = created;
		this.edited = edited;
	}


	public int getCodigo() { return codigo; }

	public void setCodigo(int codigo) { this.codigo = codigo; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getModel() { return model; }

	public void setModel(String model) { this.model = model; }

	public String getStarship_class() { return starship_class; }

	public void setStarship_class(String starship_class) { this.starship_class = starship_class; }

	public String getManufacturer() { return manufacturer; }

	public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

	public String getCost_in_credits() { return cost_in_credits; }

	public void setCost_in_credits(String cost_in_credits) { this.cost_in_credits = cost_in_credits; }

	public String getLength() { return length; }

	public void setLength(String length) { this.length = length; }

	public String getCrew() { return crew; }

	public void setCrew(String crew) { this.crew = crew; }

	public String getPassengers() { return passengers; }

	public void setPassengers(String passengers) { this.passengers = passengers; }

	public String getMax_atmosphering_speed() { return max_atmosphering_speed; }

	public void setMax_atmosphering_speed(String max_atmosphering_speed) { this.max_atmosphering_speed = max_atmosphering_speed; }

	public String getHyperdrive_rating() { return hyperdrive_rating; }

	public void setHyperdrive_rating(String hyperdrive_rating) {this.hyperdrive_rating = hyperdrive_rating; }

	public String getMGLT() { return MGLT;}

	public void setMGLT(String mGLT) { MGLT = mGLT; }

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
		return "Starships [codigo: " + codigo + ", name: " + name + ", model: " + model + 
				", starship_class: "+ starship_class + ", manufacturer: " + manufacturer +
				", cost_in_credits: " + cost_in_credits+ ", length: " + length +
				", crew: " + crew + ", passengers: " + passengers +
				", max_atmosphering_speed: "+ max_atmosphering_speed + ", hyperdrive_rating: " + hyperdrive_rating +
				", MGLT=" + MGLT+ ", cargo_capacity: " + cargo_capacity + ", consumables: " + consumables + 
				", created: " + created + ", edited: " + edited + "]";
	}
}
