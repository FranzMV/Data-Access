// default package
// Generated Jan 7, 2022, 7:42:05 PM by Hibernate Tools 5.5.7.Final
package com.fran.starwars.model;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * Planets generated by hbm2java
 * @author Francisco David Manzanedo.
 */
@NamedQueries({
	
	@NamedQuery(
		name = "Planets.findByName",
		query = "select p from Planets p where lower(p.name) like lower(concat('%', :name,'%'))"),
	@NamedQuery(		
		name = "Planets.withoutSpecies",
		query = "select planet from Planets planet where planet.codigo not in (select people.planets from People people)"),
})


@Entity
@Table(name = "planets", schema = "public", catalog = "starwars")
public class Planets implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "codigo", unique = true, nullable = false)
	private int codigo;
	private String name;
	private String diameter;
	@Column(name = "rotation_period")
	private String rotationPeriod;
	@Column(name = "orbital_period")
	private String orbitalPeriod;
	private String gravity;
	private String population;
	private String climate;
	private String terrain;
	@Column(name = "surface_water")
	private String surfaceWater;
	private String created;
	private String edited;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "planets")
	private Set<Species> species = new HashSet<Species>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "planets")
	private Set<People> people = new HashSet<People>();
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "films_planets", 
		joinColumns = { @JoinColumn(name = "codigo_planet", nullable = false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "codigo_film", nullable = false, updatable = false) })
	private Set<Films> films = new HashSet<Films>();

	public Planets() {
	}

	public Planets(int codigo) {
		this.codigo = codigo;
	}

	public Planets(int codigo, String name, String diameter, String rotationPeriod, String orbitalPeriod,
			String gravity, String population, String climate, String terrain, String surfaceWater, String created,
			String edited, Set<Species> species, Set<People> people, Set<Films> films) {
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
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiameter() {
		return this.diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public String getRotationPeriod() {
		return this.rotationPeriod;
	}

	public void setRotationPeriod(String rotationPeriod) {
		this.rotationPeriod = rotationPeriod;
	}

	public String getOrbitalPeriod() {
		return this.orbitalPeriod;
	}

	public void setOrbitalPeriod(String orbitalPeriod) {
		this.orbitalPeriod = orbitalPeriod;
	}

	public String getGravity() {
		return this.gravity;
	}

	public void setGravity(String gravity) {
		this.gravity = gravity;
	}

	public String getPopulation() {
		return this.population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public String getClimate() {
		return this.climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return this.terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public String getSurfaceWater() {
		return this.surfaceWater;
	}

	public void setSurfaceWater(String surfaceWater) {
		this.surfaceWater = surfaceWater;
	}

	public String getCreated() {
		return this.created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getEdited() {
		return this.edited;
	}

	public void setEdited(String edited) {
		this.edited = edited;
	}

	public Set<Species> getSpecies() {
		return this.species;
	}

	public void setSpecies(Set<Species> species) {
		this.species = species;
	}

	public Set<People> getPeople() {
		return this.people;
	}

	public void setPeople(Set<People> people) {
		this.people = people;
	}

	public Set<Films> getFilms() {
		return this.films;
	}

	public void setFilms(Set<Films> films) {
		this.films = films;
	}

	@Override
	public String toString() {
		return "Planets [codigo=" + codigo + ", name=" + name + ", diameter=" + diameter + ", rotationPeriod="
				+ rotationPeriod + ", orbitalPeriod=" + orbitalPeriod + ", gravity=" + gravity + ", population="
				+ population + ", climate=" + climate + ", terrain=" + terrain + ", surfaceWater=" + surfaceWater
				+ ", created=" + created + ", edited=" + edited + "]";
	}

	
	
}
