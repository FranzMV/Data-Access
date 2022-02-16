package com.fran.gestionstarwars.models.dto;

import java.io.Serializable;
import java.util.Set;


public class SpeciesDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private int codigo;
	private int planets;
	private String name;
	private String classification;
	private String designation;
	private String averageHeight;
	private String averageLifespan;
	private String eyeColors;
	private String hairColors;
	private String skinColors;
	private String language;
	private String created;
	private String edited;
	Set<Integer> people;
	
	
	public SpeciesDTO() {
		super();
	}
	
	public SpeciesDTO(int codigo) {
		this.codigo = codigo;
	}

	public SpeciesDTO(int codigo, Integer planets, String name, String classification, String designation,
			String averageHeight, String averageLifespan, String eyeColors, String hairColors, String skinColors,
			String language, String created, String edited, Set<Integer> people) {
		super();
		this.codigo = codigo;
		this.planets = planets;
		this.name = name;
		this.classification = classification;
		this.designation = designation;
		this.averageHeight = averageHeight;
		this.averageLifespan = averageLifespan;
		this.eyeColors = eyeColors;
		this.hairColors = hairColors;
		this.skinColors = skinColors;
		this.language = language;
		this.created = created;
		this.edited = edited;
		this.people = people;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Integer getPlanets() {
		return planets;
	}

	public void setPlanets(Integer planets) {
		this.planets = planets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAverageHeight() {
		return averageHeight;
	}

	public void setAverageHeight(String averageHeight) {
		this.averageHeight = averageHeight;
	}

	public String getAverageLifespan() {
		return averageLifespan;
	}

	public void setAverageLifespan(String averageLifespan) {
		this.averageLifespan = averageLifespan;
	}

	public String getEyeColors() {
		return eyeColors;
	}

	public void setEyeColors(String eyeColors) {
		this.eyeColors = eyeColors;
	}

	public String getHairColors() {
		return hairColors;
	}

	public void setHairColors(String hairColors) {
		this.hairColors = hairColors;
	}

	public String getSkinColors() {
		return skinColors;
	}

	public void setSkinColors(String skinColors) {
		this.skinColors = skinColors;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
	
	
}
