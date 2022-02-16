package com.fran.swapi.entidades;

import com.google.gson.annotations.Expose;

/**
 * @author Francisco David Manzanedo Valle
 *
 */


public class Species {

	
	private int codigo;
	@Expose
	private String name;
	@Expose
	private String classification;
	@Expose
	private String designation;
	@Expose
	private String average_height;
	@Expose
	private String average_lifespan;
	@Expose
	private String eye_colors;
	@Expose
	private String hair_colors;
	@Expose
	private String skin_colors;
	@Expose
	private String language;
	private Integer homeworld;//FOREIGN KEY (homeworld) REFERENCES planets(codigo);
	@Expose
	private String created;
	@Expose
	private String edited;
	
	
	public Species() { }

	public Species(String name, String classification, String designation, String average_height,
			String average_lifespan, String eye_colors, String hair_colors, String skin_colors, String language,
			Integer homeworld, String created, String edited) {
		this.name = name;
		this.classification = classification;
		this.designation = designation;
		this.average_height = average_height;
		this.average_lifespan = average_lifespan;
		this.eye_colors = eye_colors;
		this.hair_colors = hair_colors;
		this.skin_colors = skin_colors;
		this.language = language;
		this.homeworld = homeworld;
		this.created = created;
		this.edited = edited;
	}
	
	public Species(int codigo, String name, String classification, String designation, String average_height,
			String average_lifespan, String eye_colors, String hair_colors, String skin_colors, String language,
			Integer homeworld, String created, String edited) {
		this.codigo = codigo;
		this.name = name;
		this.classification = classification;
		this.designation = designation;
		this.average_height = average_height;
		this.average_lifespan = average_lifespan;
		this.eye_colors = eye_colors;
		this.hair_colors = hair_colors;
		this.skin_colors = skin_colors;
		this.language = language;
		this.homeworld = homeworld;
		this.created = created;
		this.edited = edited;
	}


	public int getCodigo() { return codigo; }

	public void setCodigo(int codigo) { this.codigo = codigo; }

	public String getName() { return name;}

	public void setName(String name) { this.name = name; }

	public String getClassification() { return classification; }

	public void setClassification(String classification) { this.classification = classification; }

	public String getDesignation() { return designation; }

	public void setDesignation(String designation) {this.designation = designation; }

	public String getAverage_height() { return average_height; }

	public void setAverage_height(String average_height) { this.average_height = average_height; }

	public String getAverage_lifespan() { return average_lifespan; }

	public void setAverage_lifespan(String average_lifespan) { this.average_lifespan = average_lifespan; }

	public String getEye_colors() { return eye_colors; }

	public void setEye_colors(String eye_colors) { this.eye_colors = eye_colors; }

	public String getHair_colors() { return hair_colors; }

	public void setHair_colors(String hair_colors) { this.hair_colors = hair_colors; }

	public String getSkin_colors() { return skin_colors; }

	public void setSkin_colors(String skin_colors) { this.skin_colors = skin_colors; }

	public String getLanguage() { return language; }

	public void setLanguage(String language) { this.language = language; }

	public Integer getHomeworld() { return homeworld; }

	public void setHomeworld(Integer homeworld) { this.homeworld = homeworld; }

	public String getCreated() { return created; }

	public void setCreated(String created) { this.created = created; }

	public String getEdited() { return edited; }

	public void setEdited(String edited) { this.edited = edited; }


	@Override
	public String toString() {
		return "Species [codigo: " + codigo + ", name: " + name + ", classification: " + classification +
				", designation: "+ designation + ", average_height: " + average_height +
				", average_lifespan: " + average_lifespan+ ", eye_color: " + eye_colors + 
				", hair_color: " + hair_colors + ", skin_color: " + skin_colors
				+ ", language: " + language + ", homeworld: " + homeworld + 
				", created: " + created + ", edited=" + edited+ "]";
	}
}
