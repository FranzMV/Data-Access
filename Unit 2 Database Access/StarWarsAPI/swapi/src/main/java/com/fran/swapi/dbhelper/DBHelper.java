package com.fran.swapi.dbhelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**  @author Francisco David Manzanedo */
public class DBHelper {
	

  /**
    * Contiene el nombre de las tablas de la base de datos
    * @return List<Object> con el nombre de todas las tablas de la base de datos
    */
    public static List<Object> getTableNames(){
	    return new ArrayList<Object>(Arrays.asList(
				"films_planets",
				"species_people",
				"species",
				"starships_people",
			    "vehicles_people",
			    "films_people",
			    "people",
			    "planets",
			    "starships_films",
			    "starships",
			    "vehicles_films",
			    "vehicles",
			    "films"
		));
    }
    
    /**
     * Crea todas las tablas de la base de datos
     * @return ArrayList con todos los CREATE TABLE
     */
	public static List<Object> createTables(){
    	return  new ArrayList<Object>(Arrays.asList(
    		
    			"CREATE TABLE films(\r\n" + 
    			"\r\n" + 
    			" 	codigo INTEGER PRIMARY KEY,\r\n" + 
    			" 	title VARCHAR(100),\r\n" + 
    			" 	episode_id VARCHAR(100),\r\n" + 
    			" 	opening_crawl VARCHAR(10485760),\r\n" + 
    			" 	director VARCHAR(100),\r\n" + 
    			" 	producer VARCHAR(100),\r\n" + 
    			" 	release_date VARCHAR(100),\r\n" + 
    			" 	created VARCHAR(100),\r\n" + 
    			" 	edited VARCHAR(100)\r\n" + 
    			");",
    			
    			" CREATE TABLE vehicles(\r\n" + 
    			"\r\n" + 
    			" 	codigo INTEGER PRIMARY KEY,\r\n" + 
    			" 	name VARCHAR (100),\r\n" + 
    			" 	model VARCHAR (100),\r\n" + 
    			" 	vehicle_class VARCHAR (100),\r\n" + 
    			" 	manufacturer VARCHAR (100),	\r\n" + 
    			" 	length VARCHAR (100),\r\n" + 
    			" 	cost_in_credits VARCHAR (100),\r\n" + 
    			" 	crew VARCHAR (100),\r\n" + 
    			" 	passengers VARCHAR (100),\r\n" + 
    			" 	max_atmosphering_speed VARCHAR (100),\r\n" + 
    			" 	cargo_capacity VARCHAR (100),\r\n" + 
    			" 	consumables VARCHAR (100),\r\n" + 
    			" 	created VARCHAR (100),\r\n" + 
    			" 	edited VARCHAR (100)\r\n" + 
    			");",
    			
    			"CREATE TABLE vehicles_films(\r\n" + 
    			"\r\n" + 
    			" 	codigo_vehicle INTEGER NOT NULL,\r\n" + 
    			" 	codigo_film INTEGER NOT NULL,\r\n" + 
    			" 	PRIMARY KEY(codigo_vehicle, codigo_film),\r\n" + 
    			" 	FOREIGN KEY (codigo_vehicle) REFERENCES vehicles(codigo),\r\n" + 
    			" 	FOREIGN KEY (codigo_film) REFERENCES films(codigo)\r\n" + 
    			");",
    			
    			"CREATE TABLE starships (\r\n" + 
    			"\r\n" + 
    			" 	codigo INTEGER PRIMARY KEY,\r\n" + 
    			" 	name VARCHAR(100),\r\n" + 
    			" 	model VARCHAR(100),\r\n" + 
    			" 	starship_class VARCHAR(100),\r\n" + 
    			" 	manufacturer VARCHAR(100),\r\n" + 
    			" 	cost_in_credits VARCHAR(100),\r\n" + 
    			" 	length VARCHAR(100),\r\n" + 
    			" 	crew VARCHAR(100),\r\n" + 
    			" 	passengers VARCHAR(100),\r\n" + 
    			" 	max_atmosphering_speed VARCHAR(100),\r\n" + 
    			" 	hyperdrive_rating VARCHAR(100),\r\n" + 
    			" 	MGLT VARCHAR(100),\r\n" + 
    			" 	cargo_capacity VARCHAR(100),\r\n" + 
    			" 	consumables VARCHAR(100),\r\n" + 
    			" 	created VARCHAR(100),\r\n" + 
    			" 	edited VARCHAR(100)\r\n" + 
    			");",
    			
    			" CREATE TABLE starships_films(\r\n" + 
    			"\r\n" + 
    			" 	codigo_starship INTEGER NOT NULL,\r\n" + 
    			" 	codigo_film INTEGER NOT NULL,\r\n" + 
    			" 	PRIMARY KEY(codigo_starship, codigo_film),\r\n" + 
    			" 	FOREIGN KEY (codigo_starship) REFERENCES starships(codigo),\r\n" + 
    			" 	FOREIGN KEY (codigo_film) REFERENCES films(codigo)\r\n" + 
    			");",
    			
    			"CREATE TABLE planets(\r\n" + 
    			"	\r\n" + 
    			"	codigo INTEGER PRIMARY KEY,\r\n" + 
    			"	name VARCHAR(100),\r\n" + 
    			"	diameter VARCHAR(100),\r\n" + 
    			"	rotation_period VARCHAR(100),\r\n" + 
    			"	orbital_period VARCHAR(100),\r\n" + 
    			"	gravity VARCHAR(100),\r\n" + 
    			"	population VARCHAR(100),\r\n" + 
    			"	climate VARCHAR(100),\r\n" + 
    			"	terrain VARCHAR(100),\r\n" + 
    			"	surface_water VARCHAR(100),\r\n" + 
    			"	created VARCHAR(100),\r\n" + 
    			"	edited VARCHAR(100)\r\n" + 
    			");",
    			
    			"CREATE TABLE people(\r\n" + 
    			"\r\n" + 
    			" 	codigo INTEGER PRIMARY KEY,\r\n" + 
    			" 	name VARCHAR(100),\r\n" + 
    			" 	birth_year VARCHAR(100),\r\n" + 
    			" 	eye_color VARCHAR(100),\r\n" + 
    			" 	gender VARCHAR(100),\r\n" + 
    			" 	hair_color VARCHAR(100),\r\n" + 
    			" 	height VARCHAR(100),\r\n" + 
    			" 	mass VARCHAR(100),\r\n" + 
    			" 	skin_color VARCHAR(100),\r\n" + 
    			" 	homeworld INTEGER,\r\n" + 
    			" 	created VARCHAR(100),\r\n" + 
    			" 	edited VARCHAR(100),\r\n" + 
    			" 	FOREIGN KEY (homeworld) REFERENCES planets(codigo)\r\n" + 
    			"\r\n" + 
    			");",
    			
    			"CREATE TABLE films_people(\r\n" + 
    			"\r\n" + 
    			" 	codigo_film INTEGER NOT NULL,\r\n" + 
    			" 	codigo_people INTEGER NOT NULL,\r\n" + 
    			" 	PRIMARY KEY(codigo_film, codigo_people),\r\n" + 
    			" 	FOREIGN KEY (codigo_film) REFERENCES films(codigo),\r\n" + 
    			" 	FOREIGN KEY (codigo_people) REFERENCES people(codigo)\r\n" + 
    			");",
    			
    			"CREATE TABLE vehicles_people(\r\n" + 
    			"\r\n" + 
    			"	codigo_vehicle INTEGER NOT NULL,\r\n" + 
    			"	codigo_people INTEGER NOT NULL,\r\n" + 
    			"	PRIMARY KEY(codigo_vehicle, codigo_people),\r\n" + 
    			"	FOREIGN KEY (codigo_vehicle) REFERENCES vehicles(codigo),\r\n" + 
    			"	FOREIGN KEY (codigo_people) REFERENCES people(codigo)\r\n" + 
    			");",
    			
    			"CREATE TABLE starships_people(\r\n" + 
    			"\r\n" + 
    			"	codigo_starship INTEGER NOT NULL,\r\n" + 
    			"	codigo_people INTEGER NOT NULL,\r\n" + 
    			"	PRIMARY KEY(codigo_starship, codigo_people),\r\n" + 
    			"	FOREIGN KEY (codigo_starship) REFERENCES starships(codigo),\r\n" + 
    			"	FOREIGN KEY (codigo_people) REFERENCES people(codigo)\r\n" + 
    			"\r\n" + 
    			");",
    			
    			"CREATE TABLE species(\r\n" + 
    			"\r\n" + 
    			"	codigo INTEGER PRIMARY KEY,\r\n" + 
    			"	name VARCHAR(100),\r\n" + 
    			"	classification VARCHAR(100),\r\n" + 
    			"	designation VARCHAR(100),\r\n" + 
    			"	average_height VARCHAR(100),\r\n" + 
    			"	average_lifespan VARCHAR(100),\r\n" + 
    			"	eye_color VARCHAR(100),\r\n" + 
    			"	hair_color VARCHAR(100),\r\n" + 
    			"	skin_color VARCHAR(100),\r\n" + 
    			"	language VARCHAR(100),\r\n" + 
    			"	homeworld INTEGER,\r\n" + 
    			"	created VARCHAR(100),\r\n" + 
    			"	edited VARCHAR(100),\r\n" + 
    			"	FOREIGN KEY (homeworld) REFERENCES planets(codigo)\r\n" + 
    			");",
    			
    			"CREATE TABLE species_people(\r\n" + 
    			"\r\n" + 
    			"	codigo_specie INTEGER NOT NULL,\r\n" + 
    			"	codigo_people INTEGER NOT NULL,\r\n" + 
    			"	PRIMARY KEY(codigo_specie, codigo_people),\r\n" + 
    			"	FOREIGN KEY (codigo_specie) REFERENCES species(codigo),\r\n" + 
    			"	FOREIGN KEY (codigo_people) REFERENCES people(codigo)\r\n" + 
    			");",
    			
    			"CREATE TABLE films_planets(\r\n" + 
    			"\r\n" + 
    			"	codigo_film INTEGER NOT NULL,\r\n" + 
    			"	codigo_planet INTEGER NOT NULL,\r\n" + 
    			"	PRIMARY KEY(codigo_film, codigo_planet),\r\n" + 
    			"	FOREIGN KEY (codigo_film) REFERENCES films(codigo),\r\n" + 
    			"	FOREIGN KEY (codigo_planet) REFERENCES planets(codigo)\r\n" + 
    			");"
    			
		));
    }
}
