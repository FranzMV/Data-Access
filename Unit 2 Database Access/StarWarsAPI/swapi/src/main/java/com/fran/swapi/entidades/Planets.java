package com.fran.swapi.entidades;

import com.google.gson.annotations.Expose;

/**
 * @author Francisco David Manzanedo Valle
 */



public class Planets {
	
	private int codigo;
	@Expose
	private String name;
	@Expose
	private String diameter;
	@Expose
	private String rotation_period;
	@Expose
	private String orbital_period;
	@Expose
	private String gravity;
	@Expose
	private String population;
	@Expose
	private String climate;
	@Expose
	private String terrain;
	@Expose
	private String surface_water;
	@Expose
	private String created;
	@Expose
	private String edited;

	
	public Planets() { }

	
	public Planets(String name, String diameter, String rotation_period, String orbital_period,
			String gravity, String population, String climate, String terrain, String surface_water, String created,
			String edited) {
		this.name = name;
		this.diameter = diameter;
		this.rotation_period = rotation_period;
		this.orbital_period = orbital_period;
		this.gravity = gravity;
		this.population = population;
		this.climate = climate;
		this.terrain = terrain;
		this.surface_water = surface_water;
		this.created = created;
		this.edited = edited;
	}
	
	public Planets(int codigo, String name, String diameter, String rotation_period, String orbital_period,
			String gravity, String population, String climate, String terrain, String surface_water, String created,
			String edited) {
		this.codigo = codigo;
		this.name = name;
		this.diameter = diameter;
		this.rotation_period = rotation_period;
		this.orbital_period = orbital_period;
		this.gravity = gravity;
		this.population = population;
		this.climate = climate;
		this.terrain = terrain;
		this.surface_water = surface_water;
		this.created = created;
		this.edited = edited;
	}


	public int getCodigo() { return codigo; }

	public void setCodigo(int codigo) { this.codigo = codigo; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getDiameter() { return diameter; }

	public void setDiameter(String diameter) { this.diameter = diameter; }

	public String getRotation_period() { return rotation_period; }

	public void setRotation_period(String rotation_period) { this.rotation_period = rotation_period; }

	public String getOrbital_period() { return orbital_period; }

	public void setOrbital_period(String orbital_period) { this.orbital_period = orbital_period; }

	public String getGravity() { return gravity; }

	public void setGravity(String gravity) { this.gravity = gravity; }

	public String getPopulation() { return population; }

	public void setPopulation(String population) { this.population = population; }

	public String getClimate() { return climate; }

	public void setClimate(String climate) { this.climate = climate; }

	public String getTerrain() { return terrain; }

	public void setTerrain(String terrain) { this.terrain = terrain; }

	public String getSurface_water() { return surface_water; }

	public void setSurface_water(String surface_water) { this.surface_water = surface_water; }

	public String getCreated() { return created; }

	public void setCreated(String created) { this.created = created; }

	public String getEdited() { return edited; }

	public void setEdited(String edited) { this.edited = edited; }


	@Override
	public String toString() {
		return "Planets "+
				"["+
					"codigo: " + codigo + ", name: " + name + ", diameter: " + diameter +
					", rotation_period: "+ rotation_period + ", orbital_period: " + orbital_period +
					", gravity: " + gravity + ", population: "+ population + ", climate: " + climate + 
					", terrain: " + terrain + ", surface_water: " + surface_water+ 
					", created=" + created + ", edited=" + edited + 
				"]";
	}
	
	
	
}
