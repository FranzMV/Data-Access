package com.fran.starwars.menuoptions;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fran.starwars.model.Films;
import com.fran.starwars.model.People;
import com.fran.starwars.model.Planets;
import com.fran.starwars.model.Species;
import com.fran.starwars.model.Starships;
import com.fran.starwars.model.Vehicles;
import com.fran.starwars.utilities.FunctionsUtils;
import com.fran.starwars.utilities.HibernateUtilities;

/**
 * @author Francisco David Manzanedo
 */
public class CreateElements {

	private final static String TYPE_PEOPLE = "People";
	private final static String TYPE_PLANETS = "Planets";
	private final static String TYPE_FILMS = "Films";
	private final static String TYPE_STARSHIPS = "Starships";
    //private static SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	
	/**
	 * Solicita los datos al usuario y añade un nuevo Personaje
	 */
	public static void addNewPeople() {
		int codigo = HibernateUtilities.getLastCodigo(TYPE_PEOPLE);
		String name = FunctionsUtils.validateString("Nombre: ", 100);
		LocalDate birthYear = FunctionsUtils.validateLocalDate("Fecha de nacimiento (dd-mm-yyyy): ");
		String eyeColor = FunctionsUtils.validateString("Color de ojos: ",100);
		String gender = FunctionsUtils.validateString("Género: ", 100);
		String hairColor = FunctionsUtils.validateString("Color de pelo: ", 100);
		String height = FunctionsUtils.validateString("Altura: ", 100);
		String mass = FunctionsUtils.validateString("Peso: ", 100);
		String skinColor = FunctionsUtils.validateString("Color de piel: ", 100);
		LocalDate created = FunctionsUtils.validateLocalDate("Fecha de creación (dd-mm-yyyy): ");
		LocalDate edited = FunctionsUtils.validateLocalDate("Fecha de edición (dd-mm-yyyy): ");
		Planets homeWorld = CreateElements.setPlanet();
		Set<Starships> starships = CreateElements.setStarships();
		Set<Films> films = CreateElements.setFilms();
		Set<Species> species = CreateElements.setSpecies();
		Set<Vehicles> vehicles = CreateElements.setVehicles();
		
		
		People newPeople = new People(codigo, homeWorld, name, birthYear.toString(), eyeColor,
				gender, hairColor, height, mass, skinColor, created.toString(), edited.toString(), 
				starships, species, vehicles, films);
		boolean resultSave = HibernateUtilities.save(newPeople);
		if(resultSave) System.out.println("Añadido correctamente");
		else System.out.println("No se ha podido añadir");	
	}
	
	
	/**
    * Solicita los datos al usuario y añade un nuevo Planeta 
 	*/
	public static void addPlanets() {
		int codigo = HibernateUtilities.getLastCodigo(TYPE_PLANETS);
		String name = FunctionsUtils.validateString("Nombre: ", 100);
		String diameter = FunctionsUtils.validateString("Diámetro: ", 100);
		String rotationPeriod = FunctionsUtils.validateString("Periodo de rotacional: ", 100);
		String orbitalPeriod = FunctionsUtils.validateString("Periodo orbital : ", 100);
		String gravity = FunctionsUtils.validateString("Gravedad: ", 100);
		String population = FunctionsUtils.validateString("Población: ", 100);
		String climate = FunctionsUtils.validateString("Clima: ", 100);
		String terrain = FunctionsUtils.validateString("Terreno: ", 100);
		String surfaceWater = FunctionsUtils.validateString("Superficie de agua: ", 100);
		LocalDate created = FunctionsUtils.validateLocalDate("Fecha de creación (dd-mm-yyyy): ");
		LocalDate edited = FunctionsUtils.validateLocalDate("Fecha de edición (dd-mm-yyyy): ");
		Set<Species> species = CreateElements.setSpecies();
		Set<People> people = CreateElements.setPeople();
		Set<Films> films =CreateElements.setFilms();
		
		Planets newPlanet = new Planets(codigo, name, diameter, rotationPeriod, orbitalPeriod, gravity, population,
				climate, terrain, surfaceWater, created.toString(), edited.toString(), species, people, films);
		boolean resulPlanets = HibernateUtilities.save(newPlanet);
		if(resulPlanets) System.out.println("Creado");
	}
	
	/**
	 * Solicita los datos al usuario y añade una nueva película
	 */
	public static void addFilms() {
		int codigo = HibernateUtilities.getLastCodigo(TYPE_FILMS);
		String title = FunctionsUtils.validateString("Título: ", 100);
		String episodeId = String.valueOf(codigo);
		String openingCrawl = FunctionsUtils.validateString("Introducción: ",10485760);
		String director = FunctionsUtils.validateString("Director: ", 100);
		String productor = FunctionsUtils.validateString("Productor: ", 100);
		LocalDate releaseDate = FunctionsUtils.validateLocalDate("Fecha de estreno (dd-mm-yyyy): ");
		LocalDate created = FunctionsUtils.validateLocalDate("Fechad de creación (dd-mm-yyyy): ");
		LocalDate edited = FunctionsUtils.validateLocalDate("Fecha de edición (dd-mm-yyyy): ");
		Set<People> people = CreateElements.setPeople();
		Set<Planets> planets = CreateElements.setPlanets();
		Set<Vehicles> vehicles = CreateElements.setVehicles();
		Set<Starships> starships = CreateElements.setStarships();
		
		Films newFilms = new Films(codigo, title, episodeId, openingCrawl, director, productor, releaseDate.toString(),
				created.toString(),edited.toString(), starships, planets, people, vehicles);
		boolean resultFilm = HibernateUtilities.save(newFilms);
		if(resultFilm) System.out.println("Creado!");
	}
	
	
	/**
	 * Solicita los datos al usuario y añade una nueva nave
	 */
	public static void addStarships() {
		int codigo = HibernateUtilities.getLastCodigo(TYPE_STARSHIPS);
		String name = FunctionsUtils.validateString("Nombre: ", 100);
		String model = FunctionsUtils.validateString("Modelo: ", 100);
		String starshipClass = FunctionsUtils.validateString("Tipo de nave: ", 100);
		String manufacturer = FunctionsUtils.validateString("Creador: ", 100);
		String costInCredits = FunctionsUtils.validateString("Coste en créditos: ", 100);
		String length = FunctionsUtils.validateString("Longitud: ", 100);
		String crew = FunctionsUtils.validateString("Tripulación: ", 100);
		String passengers = FunctionsUtils.validateString("Pasageros: ", 100);
		String maxAtmospheringSpeed = FunctionsUtils.validateString("Velocidad máxima atmosférica: ", 100);
		String hyperdriveRating = FunctionsUtils.validateString("Hyperdrive rating: ", 100);
		String MGLT = FunctionsUtils.validateString("MGLT: ", 100);
		String cargoCapacity = FunctionsUtils.validateString("Capacidad de carga: ", 100);
		String consumables = FunctionsUtils.validateString("Consumibles: ", 100);
		LocalDate created = FunctionsUtils.validateLocalDate("Fecha de creación (dd-mm-yyyy): ");
		LocalDate edited = FunctionsUtils.validateLocalDate("Fecha de edición (dd-mm-yyyy): ");
		Set<Films> films = CreateElements.setFilms();
		Set<People> people = CreateElements.setPeople();
		
		Starships newStarship = new Starships(codigo, name, model, starshipClass, manufacturer, costInCredits, length,
				crew, passengers, maxAtmospheringSpeed, hyperdriveRating, MGLT, cargoCapacity, consumables, 
				created.toString(), edited.toString(), films, people);

		boolean resultStarships = HibernateUtilities.save(newStarship);
		if(resultStarships) System.out.println("Creado");
	}
	

	/**
	 * Obtiene el planeta seleccionado por el usuario.
	 * @return Objeto de tipo Planets seleccionado por el usuario.
	 */
	public static Planets setPlanet(){
		Planets resultPlanets = new Planets();
		List<Planets> planets = HibernateUtilities.devolverClase(Planets.class);
		boolean validPlanet = false;
		System.out.println("\n####PLANETS####");
		planets.forEach(p-> System.out.println("Código: "+p.getCodigo()+"; Nombre: "+p.getName()));
		System.out.println();
		
		do {
			String homeWorld = FunctionsUtils.validateString("Introduzca el código del planeta: ", 3);
			for(Planets p: planets) {
				if(Integer.parseInt(homeWorld) == p.getCodigo()) {
					resultPlanets = (p);
					validPlanet = true;
				}
			}
			if(!validPlanet)
				System.out.print("El código introducido no es válido, pruebe de nuevo: ");
			
		}while(!validPlanet);
		System.out.println();
		return resultPlanets;
	}
	
	
	/**
	 * Obtiene las naves seleccionadas por el usuario.
	 * @return Set de starships seleccionadas por el usuario.
	 */
	public static Set<Starships> setStarships(){
		Set<Starships> resultStarships = new HashSet<Starships>();
		List<Starships> starships = HibernateUtilities.devolverClase(Starships.class);
		boolean validStarship = false;
		String codigoStarship;
		System.out.println("\n####STARSHIPS####");
		starships.forEach(s-> System.out.println("Código: "+s.getCodigo()+"; Nombre: "+s.getName()));
		System.out.println();
		
		do {
			codigoStarship =FunctionsUtils.validateString("Introduzca el código de Starships (0 para terminar): ", 3);
			if(!codigoStarship.equals("0")) {
				for(Starships s: starships) {
					if(Integer.parseInt(codigoStarship) == s.getCodigo()) {
						resultStarships.add(s);
						validStarship = true;
					}
				}
			}
			if(!validStarship)
				System.out.print("El código introducido no es válido, pruebe de nuevo: ");
			
		} while (!codigoStarship.equals("0"));
		System.out.println();
		return resultStarships;
	}
	
	
	/**
	 * Obtiene las species seleccionadas por el usuario.
	 * @return Set the species seleccionadas por el usuario.
	 */
	public static Set<Species> setSpecies(){
		Set<Species> resultSpecies = new HashSet<Species>();
		List<Species> species = HibernateUtilities.devolverClase(Species.class);
		boolean validSpecie = false;
		String codigoSpecie;
		System.out.println("\n####SPECIES####");
		species.forEach(s-> System.out.println("Código: "+s.getCodigo()+"; Nombre: "+s.getName()));
		System.out.println();
		
	
		do {
			codigoSpecie = FunctionsUtils.validateString("Introduzca el código de la especie (0 para terminar): ", 3);
			if(!codigoSpecie.equals("0")) {
				for(Species s: species) {
					if(Integer.parseInt(codigoSpecie) == s.getCodigo()) {
						resultSpecies.add(s);
						validSpecie = true;
					}
				}
			}
			if(!validSpecie)
				System.out.print("El código introducido no es válido, pruebe de nuevo: ");
			
		} while (!codigoSpecie.equals("0"));
		System.out.println();
		return resultSpecies;
	}
	
	
	/**
	 * Obtiene los vehículos seleccionados por el usuario.
	 * @return Set de vehiculos seleccionados por el usuario.
	 */
	public static Set<Vehicles> setVehicles(){
		Set<Vehicles> resultVehicles = new HashSet<Vehicles>();
		List<Vehicles> vehicles = HibernateUtilities.devolverClase(Vehicles.class);
		boolean validVehicle = false;
		String codigoVehicle;
		System.out.println("####VEHICLES####");
		vehicles.forEach(v-> System.out.println("Código: "+v.getCodigo()+"; Nombre: "+v.getName()));
		System.out.println();
		
		
		do {
			codigoVehicle =FunctionsUtils.validateString("Introduzca el código del vehículo (0 para terminar): ", 3);
			if(!codigoVehicle.equals("0")) {
				for(Vehicles v: vehicles) {
					if(Integer.parseInt(codigoVehicle) == v.getCodigo()) {
						resultVehicles.add(v);
						validVehicle = true;
					}
				}
			}
			if(!validVehicle)
				System.out.print("El código introducido no es válido, pruebe de nuevo: ");
			
		} while (!codigoVehicle.equals("0"));
		System.out.println();
		return resultVehicles;
	}
	
	
	/**
	 * Obtiene los films seleccionados por el usuario. 
	 * @return Set de films seleccionados por el usuario.
	 */
	public static Set<Films> setFilms(){
		Set<Films> resultFilms = new HashSet<Films>();
		List<Films> films = HibernateUtilities.devolverClase(Films.class);
		boolean validFilm = false;
		String codigoFilm;
		System.out.println("####FILMS####");
		films.forEach(f-> System.out.println("Código: "+f.getCodigo()+"; Título: "+f.getTitle()));
		System.out.println();
		
		do {
			codigoFilm = FunctionsUtils.validateString("Introduzca el código del Film (0 para terminar): ", 3);
			if(!codigoFilm.equals("0")) {
				for(Films f: films) {
					if(Integer.parseInt(codigoFilm) == f.getCodigo()) {
						resultFilms.add(f);
						validFilm = true;
					}
				}
			}
			if(!validFilm)
				System.out.print("El código introducido no es válido, pruebe de nuevo: ");
			
		} while (!codigoFilm.equals("0"));
		
		return resultFilms;
	}
	
	/**
	 * Obtiene los personajes seleccionados por el usuario. 
	 * @return Set de personajes seleccionados por el usuario.
	 */
	public static Set<People> setPeople(){
		Set<People> resultPeople = new HashSet<People>();
		List<People> people = HibernateUtilities.devolverClase(People.class);
		boolean validPeople = false;
		String codigoPeople;
		System.out.println("####PEOPLE####");
		people.forEach(f-> System.out.println("Código: "+f.getCodigo()+"; Nombre: "+f.getName()));
		System.out.println();
		
		do {
			codigoPeople = FunctionsUtils.validateString("Introduzca el código del Personaje (0 para terminar): ", 3);
			if(!codigoPeople.equals("0")) {
				for(People p: people) {
					if(Integer.parseInt(codigoPeople) == p.getCodigo()) {
						resultPeople.add(p);
						validPeople = true;
					}
				}
			}
			if(!validPeople)
				System.out.print("El código introducido no es válido, pruebe de nuevo: ");
			
		} while (!codigoPeople.equals("0"));
		
		return resultPeople;
	}
	
	/**
	 * Obtiene los planetas seleccionados por el usuario. 
	 * @return Set de planetas seleccionados por el usuario.
	 */
	public static Set<Planets> setPlanets(){
		Set<Planets> resultPlanets = new HashSet<Planets>();
		List<Planets> planets = HibernateUtilities.devolverClase(Planets.class);
		boolean validPlanet = false;
		String codigoPlanet;
		System.out.println("####PLANETS####");
		planets.forEach(f-> System.out.println("Código: "+f.getCodigo()+"; Nombre: "+f.getName()));
		System.out.println();
		
		do {
			codigoPlanet = FunctionsUtils.validateString("Introduzca el código del Planeta (0 para terminar): ", 3);
			if(!codigoPlanet.equals("0")) {
				for(Planets p: planets) {
					if(Integer.parseInt(codigoPlanet) == p.getCodigo()) {
						resultPlanets.add(p);
						validPlanet = true;
					}
				}
			}
			if(!validPlanet)
				System.out.print("El código introducido no es válido, pruebe de nuevo: ");
			
		} while (!codigoPlanet.equals("0"));
		
		return resultPlanets;
	}
}
