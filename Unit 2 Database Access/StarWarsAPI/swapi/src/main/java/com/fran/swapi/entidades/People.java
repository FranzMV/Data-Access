package com.fran.swapi.entidades;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * @author Francisco David Manzanedo Valle
 */


public class People implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int codigo;
	@Expose
	private String name;
	@Expose
	private String birth_year;
	@Expose
	private String eye_color;
	@Expose
	private String gender;
	@Expose
	private String hair_color;
	@Expose
	private String height;
	@Expose
	private String mass;
	@Expose
	private String skin_color;
	private Integer homeworld;//FOREIGN KEY (homeworld) REFERENCES planets(codigo)
	@Expose
	private String created;
	@Expose
	private String edited;
	
	
	public People() { }

	
	public People(String name, String birth_year, String eye_color, String gender, String hair_color,
			String height, String mass, String skin_color, Integer homeworld, String created, String edited) {
		this.name = name;
		this.birth_year = birth_year;
		this.eye_color = eye_color;
		this.gender = gender;
		this.hair_color = hair_color;
		this.height = height;
		this.mass = mass;
		this.skin_color = skin_color;
		this.homeworld = homeworld;
		this.created = created;
		this.edited = edited;
	}
	
	public People(int codigo, String name, String birth_year, String eye_color, String gender, String hair_color,
			String height, String mass, String skin_color, Integer homeworld, String created, String edited) {
		this.codigo = codigo;
		this.name = name;
		this.birth_year = birth_year;
		this.eye_color = eye_color;
		this.gender = gender;
		this.hair_color = hair_color;
		this.height = height;
		this.mass = mass;
		this.skin_color = skin_color;
		this.homeworld = homeworld;
		this.created = created;
		this.edited = edited;
	}


	public int getCodigo() { return codigo; }

	public void setCodigo(int codigo) { this.codigo = codigo; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getBirth_year() { return birth_year; }

	public void setBirth_year(String birth_year) { this.birth_year = birth_year; }

	public String getEye_color() { return eye_color; }

	public void setEye_color(String eye_color) { this.eye_color = eye_color; }

	public String getGender() { return gender; }

	public void setGender(String gender) { this.gender = gender; }

	public String getHair_color() { return hair_color; }

	public void setHair_color(String hair_color) { this.hair_color = hair_color; }

	public String getHeight() { return height; }

	public void setHeight(String height) { this.height = height; }

	public String getMass() { return mass; }

	public void setMass(String mass) { this.mass = mass; }

	public String getSkin_color() { return skin_color; }

	public void setSkin_color(String skin_color) { this.skin_color = skin_color; }

	public Integer getHomeworld() { return homeworld; }

	public void setHomeworld(Integer homeworld) { this.homeworld = homeworld; }

	public String getCreated() { return created; }

	public void setCreated(String created) { this.created = created; }

	public String getEdited() { return edited; }

	public void setEdited(String edited) { this.edited = edited; }


	@Override
	public String toString() {
		return "People [codigo: " + codigo + ", name: " + name + ", birth_year: " + birth_year + 
				", eye_color: " + eye_color+ ", gender: " + gender + ", hair_color: " + hair_color +
				", height: " + height + ", mass: " + mass + ", skin_color: " + skin_color + 
				", homeworld: " + homeworld + ", created: " + created + ", edited: "+ edited + "]";
	}
}
