/**
 * Autor: Francisco David Manzanedo Valle.
 */ 

 DROP TABLE IF EXISTS films;
 CREATE TABLE films(

 	codigo INTEGER PRIMARY KEY,
 	title VARCHAR(100),
 	episode_id VARCHAR(100),
 	opening_crawl VARCHAR(10485760),
 	director VARCHAR(100),
 	producer VARCHAR(100),
 	release_date VARCHAR(100),
 	created VARCHAR(100),
 	edited VARCHAR(100)
);

 DROP TABLE IF EXISTS vehicles;
 CREATE TABLE vehicles(

 	codigo INTEGER PRIMARY KEY,
 	name VARCHAR (100),
 	model VARCHAR (100),
 	vehicle_class VARCHAR (100),
 	manufacturer VARCHAR (100),	
 	length VARCHAR (100),
 	cost_in_credits VARCHAR (100),
 	crew VARCHAR (100),
 	passengers VARCHAR (100),
 	max_atmosphering_speed VARCHAR (100),
 	cargo_capacity VARCHAR (100),
 	consumables VARCHAR (100),
 	created VARCHAR (100),
 	edited VARCHAR (100)
);

 DROP TABLE IF EXISTS vehicles_films;
 CREATE TABLE vehicle_films(

 	codigo_vehicle INTEGER NOT NULL,
 	codigo_film INTEGER NOT NULL,
 	PRIMARY KEY(codigo_vehicle, codigo_film),
 	FOREIGN KEY (codigo_vehicle) REFERENCES vehicles(codigo),
 	FOREIGN KEY (codigo_film) REFERENCES films(codigo)
);

 DROP TABLE IF EXISTS starships;
 CREATE TABLE starships (

 	codigo INTEGER PRIMARY KEY,
 	name VARCHAR(100),
 	model VARCHAR(100),
 	starship_class VARCHAR(100),
 	manufacturer VARCHAR(100),
 	cost_in_credits VARCHAR(100),
 	length VARCHAR(100),
 	crew VARCHAR(100),
 	passengers VARCHAR(100),
 	max_atmosphering_speed VARCHAR(100),
 	hyperdrive_rating VARCHAR(100),
 	MGLT VARCHAR(100),
 	cargo_capacity VARCHAR(100),
 	consumables VARCHAR(100),
 	created VARCHAR(100),
 	edited VARCHAR(100)
);

 DROP TABLE IF EXISTS starships_films;
 CREATE TABLE starships_films(

 	codigo_starship INTEGER NOT NULL,
 	codigo_film INTEGER NOT NULL,
 	PRIMARY KEY(codigo_starship, codigo_film),
 	FOREIGN KEY (codigo_starship) REFERENCES starships(codigo),
 	FOREIGN KEY (codigo_film) REFERENCES films(codigo)
);


DROP TABLE IF EXISTS planets;
CREATE TABLE planets(
	
	codigo INTEGER PRIMARY KEY,
	name VARCHAR(100),
	diameter VARCHAR(100),
	rotation_period VARCHAR(100),
	orbital_period VARCHAR(100),
	gravity VARCHAR(100),
	population VARCHAR(100),
	climate VARCHAR(100),
	terrain VARCHAR(100),
	surface_water VARCHAR(100),
	created VARCHAR(100),
	edited VARCHAR(100)
);


 DROP TABLE IF EXISTS people;
 CREATE TABLE people(

 	codigo INTEGER PRIMARY KEY,
 	name VARCHAR(100),
 	birth_year VARCHAR(100),
 	eye_color VARCHAR(100),
 	gender VARCHAR(100),
 	hair_color VARCHAR(100),
 	height VARCHAR(100),
 	mass VARCHAR(100),
 	skin_color VARCHAR(100),
 	homeworld INTEGER,
 	created VARCHAR(100),
 	edited VARCHAR(100),
 	FOREIGN KEY (homeworld) REFERENCES planets(codigo)

);

 DROP TABLE IF EXISTS films_people;
 CREATE TABLE films_people(

 	codigo_film INTEGER NOT NULL,
 	codigo_people INTEGER NOT NULL,
 	PRIMARY KEY(codigo_film, codigo_people),
 	FOREIGN KEY (codigo_film) REFERENCES films(codigo),
 	FOREIGN KEY (codigo_people) REFERENCES people(codigo)
);

DROP TABLE IF EXISTS vehicles_people;
CREATE TABLE vehicle_people(

	codigo_vehicle INTEGER NOT NULL,
	codigo_people INTEGER NOT NULL,
	PRIMARY KEY(codigo_vehicle, codigo_people),
	FOREIGN KEY (codigo_vehicle) REFERENCES vehicles(codigo),
	FOREIGN KEY (codigo_people) REFERENCES people(codigo)
);

DROP TABLE IF EXISTS starships_people;
CREATE TABLE starships_people(

	codigo_starship INTEGER NOT NULL,
	codigo_people INTEGER NOT NULL,
	PRIMARY KEY(codigo_starship, codigo_people),
	FOREIGN KEY (codigo_starship) REFERENCES starships(codigo),
	FOREIGN KEY (codigo_people) REFERENCES people(codigo)

);

DROP TABLE IF EXISTS species;
CREATE TABLE species(

	codigo INTEGER PRIMARY KEY,
	name VARCHAR(100),
	classification VARCHAR(100),
	designation VARCHAR(100),
	average_height VARCHAR(100),
	average_lifespan VARCHAR(100),
	eye_color VARCHAR(100),
	hair_color VARCHAR(100),
	skin_color VARCHAR(100),
	language VARCHAR(100),
	homeworld INTEGER,
	created VARCHAR(100),
	edited VARCHAR(100),
	FOREIGN KEY (homeworld) REFERENCES planets(codigo)
);

DROP TABLE IF EXISTS species_people;
CREATE TABLE species_people(

	codigo_specie INTEGER NOT NULL,
	codigo_people INTEGER NOT NULL,
	PRIMARY KEY(codigo_specie, codigo_people),
	FOREIGN KEY (codigo_specie) REFERENCES species(codigo),
	FOREIGN KEY (codigo_people) REFERENCES people(codigo)
);

DROP TABLE IF EXISTS films_planets;
CREATE TABLE films_planets(

	codigo_film INTEGER NOT NULL,
	codigo_planet INTEGER NOT NULL,
	PRIMARY KEY(codigo_film, codigo_planet),
	FOREIGN KEY (codigo_film) REFERENCES films(codigo),
	FOREIGN KEY (codigo_planet) REFERENCES planets(codigo)
);

/**
 * 	DROP TABLE films_planets;
	DROP TABLE species_people;
	DROP TABLE species;
	DROP TABLE starships_people;
	DROP TABLE vehicle_people;
	DROP TABLE films_people;
	DROP TABLE people;
	DROP TABLE planets;
	DROP TABLE starships_films;
	DROP TABLE starships;
	DROP TABLE vehicle_films;
	DROP TABLE vehicles;
	DROP TABLE films;
 */