// default package
// Generated Jan 7, 2022, 7:42:05 PM by Hibernate Tools 5.5.7.Final
package com.fran.gestionstarwars.entity;
import java.util.HashSet;
import java.util.Set;
import java.io.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Films generated by hbm2java
 * @author Francisco David Manzanedo.
 */
@Entity
@Table(name = "films")
public class Films implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "codigo", unique = true, nullable  = false)
	private int codigo;
	private String title;
	@Column(name = "episode_id")
	private String episodeId;
	@Column(name = "opening_crawl")
	private String openingCrawl;
	private String director;
	private String producer;
	@Column(name = "release_date")
	private String releaseDate;
	private String created;
	private String edited;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "starships_films", 
			joinColumns = { @JoinColumn(name = "codigo_film") },
			inverseJoinColumns = { @JoinColumn(name = "codigo_starship") })
	private Set<Starships> starships = new HashSet<Starships>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "films_planets", 
			joinColumns = { @JoinColumn(name = "codigo_film") }, 
			inverseJoinColumns = { @JoinColumn(name = "codigo_planet") })
	private Set<Planets> planets = new HashSet<Planets>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "films_people", 
			joinColumns = { @JoinColumn(name = "codigo_film") }, 
			inverseJoinColumns = { @JoinColumn(name = "codigo_people") })
	private Set<People> people = new HashSet<People>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "vehicles_films", 
			joinColumns = { @JoinColumn(name = "codigo_film") }, 
			inverseJoinColumns = { @JoinColumn(name = "codigo_vehicle") })
	private Set<Vehicles> vehicles = new HashSet<Vehicles>();

	public Films() {
	}

	public Films(int codigo) {
		this.codigo = codigo;
	}

	public Films(int codigo, String title, String episodeId, String openingCrawl, String director, String producer,
			String releaseDate, String created, String edited, Set<Starships> starships, Set<Planets> planets, Set<People> people,
			Set<Vehicles> vehicles) {
		this.codigo = codigo;
		this.title = title;
		this.episodeId = episodeId;
		this.openingCrawl = openingCrawl;
		this.director = director;
		this.producer = producer;
		this.releaseDate = releaseDate;
		this.created = created;
		this.edited = edited;
		this.starships = starships;
		this.planets = planets;
		this.people = people;
		this.vehicles = vehicles;
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEpisodeId() {
		return this.episodeId;
	}

	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}

	public String getOpeningCrawl() {
		return this.openingCrawl;
	}

	public void setOpeningCrawl(String openingCrawl) {
		this.openingCrawl = openingCrawl;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getProducer() {
		return this.producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
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
	//@JsonBackReference
	public Set<Starships> getStarships() {
		return this.starships;
	}

	public void setStarshipses(Set<Starships> starships) {
		this.starships = starships;
	}
	
	public Set<Planets> getPlanets() {
		return this.planets;
	}

	public void setPlanets(Set<Planets> planets) {
		this.planets = planets;
	}
	
	
	public Set<People> getPeople() {
		return this.people;
	}

	public void setPeoples(Set<People> people) {
		this.people = people;
	}
	
	public Set<Vehicles> getVehicles() {
		return this.vehicles;
	}

	public void setVehicleses(Set<Vehicles> vehicles) {
		this.vehicles = vehicles;
	}
}
