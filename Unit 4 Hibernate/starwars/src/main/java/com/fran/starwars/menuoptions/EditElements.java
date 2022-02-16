package com.fran.starwars.menuoptions;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
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
public class EditElements {

	private static Scanner sc = new Scanner(System.in);
	
	
	/**
	 * Muestra todos los personajes y permite al usuario seleccionar uno mediante el campo código  
	 * para posteriormente poder editar sus campos.
	 */
	public static void editPeople() {
		
		List<People> people = HibernateUtilities.devolverClase(People.class);
		System.out.println();
		if(people.size() > 0) {
			people.forEach(p-> System.out.println("Código: "+p.getCodigo()+"; Nombre: "+p.getName()));
			String codigo = FunctionsUtils.validateString("\nSeleccione el código del personaje que desea editar: ", 3);
			List<People> peopleToEdit = HibernateUtilities.getElementById(People.class, codigo);
			if(peopleToEdit.size() > 0) {
				People p = peopleToEdit.get(0);
				p.setName(setNewData("Nombre", p.getName()));
				p.setBirthYear(setNewData("Fecha de nacimiento", p.getBirthYear().toString()));
				p.setEyeColor(setNewData("Color de ojos", p.getEyeColor()));
				p.setGender(setNewData("Género", p.getGender()));
				p.setHairColor(setNewData("Color de pelo: ", p.getHairColor()));
				p.setHeight(setNewData("Altura", p.getHeight()));
		        p.setMass(setNewData("Peso", p.getMass()));
		        p.setSkinColor(setNewData("Color de piel", p.getSkinColor()));
		        p.setCreated(setNewData("Creación", p.getCreated()));
				p.setEdited(String.valueOf(LocalDate.now().toString()));
				
				System.out.println("Planeta actual: ");
				System.out.println(p.getPlanets().getName());
				boolean editPlanet = FunctionsUtils.getConfirmation("¿Desea cambiar el planeta actual del personaje (si/no)?");
				if(editPlanet)  p.setPlanets(CreateElements.setPlanet());
				
				System.out.println("Naves actuales: ");
				p.getStarships().forEach(s-> System.out.println("Código: "+s.getCodigo()+"; Nombre: "+s.getName()));
				boolean editStarships = FunctionsUtils.getConfirmation("¿Desea añadir más naves (si/no)?");
				if(editStarships) { 
					Set<Starships> starshipAux = CreateElements.setStarships();
					p.getStarships().addAll(starshipAux);
				}
				
				System.out.println("Especies actuales: ");
				p.getSpecies().forEach(s-> System.out.println("Código: "+s.getCodigo()+"; Nombre: "+s.getName()));
				boolean editSpecies = FunctionsUtils.getConfirmation("¿Desea añadir más especies (si/no)?");
				if(editSpecies) {
					Set<Species> speciesAux = CreateElements.setSpecies();
					p.getSpecies().addAll(speciesAux);
				}
				
				System.out.println("Vehículos actuales: ");
				p.getVehicles().forEach(v-> System.out.println("Código: "+v.getCodigo()+"; Nombre: "+v.getName()));
				boolean editVehicles = FunctionsUtils.getConfirmation("¿Desea añadir más vehículos (si/no)?");
				if(editVehicles) {
					Set<Vehicles> vehiclesAux =CreateElements.setVehicles();
					p.getVehicles().addAll(vehiclesAux);
				}
					
				
				System.out.println("Películas actuales: ");
				p.getFilms().forEach(f-> System.out.println("Código: "+f.getCodigo()+"; Nombre: "+f.getTitle()));
				boolean editFilms = FunctionsUtils.getConfirmation("¿Desea añadir más películas (si/no)?");
				if(editFilms) { 
					Set<Films> filmsAux = CreateElements.setFilms();
					p.getFilms().addAll(filmsAux);
				}
				
				boolean result = HibernateUtilities.updateElement(p);
				if(result) System.out.println("Registro actualizado.");
				
			}else System.out.println("No se ha encontrado ningún personaje con el código "+codigo);
		}
	}
	
	/**
	 * 
	 * Muestra todos los planetas y permite al usuario seleccionar uno mediante el campo código  
	 * para posteriormente poder editar sus campos.
	 */
	 
	public static void editPlanet() {
		List<Planets> planets = HibernateUtilities.devolverClase(Planets.class);
		System.out.println();
		if(planets.size() > 0) {
			planets.forEach(p-> System.out.println("Código: "+p.getCodigo()+"; Nombre: "+p.getName()));
			String codigo = FunctionsUtils.validateString("\nSeleccione el código del planeta que desea editar: ", 3);
			List<Planets> planetToEdit = HibernateUtilities.getElementById(Planets.class, codigo);
			if(planetToEdit.size() > 0) {
				Planets p = planetToEdit.get(0);
				p.setName(setNewData("Nombre", p.getName()));
				p.setDiameter(setNewData("Diámetro", p.getDiameter()));
				p.setRotationPeriod(setNewData("Periodo de rotación", p.getRotationPeriod()));
				p.setOrbitalPeriod(setNewData("Periodo obitacional", p.getOrbitalPeriod()));
				p.setGravity(setNewData("Gravedad", p.getGravity()));
				p.setPopulation(setNewData("Población", p.getPopulation()));
				p.setClimate(setNewData("Clima", p.getClimate()));
				p.setTerrain(setNewData("Terreno", p.getTerrain()));
				p.setSurfaceWater(setNewData("Superfície de agua", p.getSurfaceWater()));
				p.setEdited(LocalDate.now().toString());
				p.setCreated(setNewData("Creación", p.getCreated().toString()));
				
				System.out.println("Películas actuales: ");
				p.getFilms().forEach(f-> System.out.println("Código: "+f.getCodigo()+"; Nombre: "+f.getTitle()));
				boolean editFilms = FunctionsUtils.getConfirmation("¿Desea añadir más películas (si/no)?");
				if(editFilms) { 
					Set<Films> filmsAux = CreateElements.setFilms();
					p.getFilms().addAll(filmsAux);
				}
				
				System.out.println("Personajes actuales: ");
				p.getPeople().forEach(pl-> System.out.println("Código: "+pl.getCodigo()+"; Nombre: "+pl.getName()));
				boolean editPeople = FunctionsUtils.getConfirmation("¿Desea añadir más personajes (si/no)?");
				if(editPeople) {
					Set<People> peopleAux = CreateElements.setPeople();
					p.getPeople().addAll(peopleAux);
				}
				
				System.out.println("Especies actuales: ");
				p.getSpecies().forEach(s-> System.out.println("Código: "+s.getCodigo()+"; Nombre: "+s.getName()));
				boolean editSpecies = FunctionsUtils.getConfirmation("¿Desea añadir más especies (si/no)?");
				if(editSpecies) {
					Set<Species> speciesAux = CreateElements.setSpecies();
					p.getSpecies().addAll(speciesAux);
				}
				
				boolean result = HibernateUtilities.updateElement(p);
				if(result) System.out.println("Registro actualizado.");
				
			}else System.out.println("No se ha encontrado ningún planeta con el código "+codigo);
		}
		
	}
	
	
	/**
	 * Muestra todos los films y permite al usuario seleccionar uno mediante el campo código  
	 * para posteriormente poder editar sus campos.
	 */
	public static void editFilm() {
		List<Films> films = HibernateUtilities.devolverClase(Films.class);
		System.out.println();
		if(films.size() > 0) {
			films.forEach(f-> System.out.println("Código: "+f.getCodigo()+"; "+f.getTitle()));
			String codigo = FunctionsUtils.validateString("\nSeleccione el código de la película que desea editar: ", 3);
			List<Films> filmToEdit = HibernateUtilities.getElementById(Films.class, codigo);
			if(filmToEdit.size() > 0) {
				Films f = filmToEdit.get(0);
				f.setTitle(setNewData("Título", f.getTitle()));
				f.setOpeningCrawl(setNewData("Introducción", f.getOpeningCrawl()));
				f.setDirector(setNewData("Director", f.getDirector()));
				f.setProducer(setNewData("Productor", f.getProducer()));
				f.setReleaseDate(setNewData("Estreno", f.getReleaseDate().toString()));
				f.setCreated(setNewData("Creación", f.getCreated().toString()));
				f.setEdited(LocalDate.now().toString());
				
				System.out.println("Naves actuales: ");
				f.getStarships().forEach(fl-> System.out.println("Código: "+fl.getCodigo()+"; Nombre: "+fl.getName()));
				boolean editStarships = FunctionsUtils.getConfirmation("¿Desea añadir más naves (si/no)?");
				if(editStarships) { 
					Set<Starships> starshipsAux = CreateElements.setStarships();
					f.getStarships().addAll(starshipsAux);
				}
				
				System.out.println("Planetas actuales:");
				f.getPlanets().forEach(p-> System.out.println("Código: "+p.getCodigo()+"; "+p.getName()));
				boolean editPlanet = FunctionsUtils.getConfirmation("¿Desea añadir más planetas (si/no)?");
				if(editPlanet) {  
					Set<Planets> planetsAux = CreateElements.setPlanets();
					f.getPlanets().addAll(planetsAux);
				}
				
				System.out.println("Personajes actuales: ");
				f.getPeople().forEach(pl-> System.out.println("Código: "+pl.getCodigo()+"; Nombre: "+pl.getName()));
				boolean editPeople = FunctionsUtils.getConfirmation("¿Desea añadir más personajes (si/no)?");
				if(editPeople) {
					Set<People> peopleAux = CreateElements.setPeople();
					f.getPeople().addAll(peopleAux);
				}
				
				System.out.println("Vehículos actuales: ");
				f.getVehicles().forEach(v-> System.out.println("Código: "+v.getCodigo()+"; Nombre: "+v.getName()));
				boolean editVehicles = FunctionsUtils.getConfirmation("¿Desea añadir más vehículos (si/no)?");
				if(editVehicles) {
					Set<Vehicles> vehiclesAux =CreateElements.setVehicles();
					f.getVehicles().addAll(vehiclesAux);
				}
				
				boolean result = HibernateUtilities.updateElement(f);
				if(result) System.out.println("Registro actualizado.");
				
			}else System.out.println("No se ha encontrado ningún Film con el código "+codigo);
		}
	}
	
	
	/**
	 * Muestra todos las naves y permite al usuario seleccionar uno mediante el campo código  
	 * para posteriormente poder editar sus campos.
	 */
	public static void editStarships() {
		List<Starships> starships = HibernateUtilities.devolverClase(Starships.class);
		System.out.println();
		if(starships.size() > 0) {
			starships.forEach(s-> System.out.println("Código: "+s.getCodigo()+"; "+s.getName()));
			String codigo = FunctionsUtils.validateString("\nSeleccione el código de la nave que desea editar: ", 3);
			List<Starships> starshipToEdit = HibernateUtilities.getElementById(Starships.class, codigo);
			if(starshipToEdit.size() > 0) {
				Starships s = starshipToEdit.get(0);
				s.setName(setNewData("Nombre", s.getName()));
				s.setModel(setNewData("Modelo", s.getModel()));
				s.setStarshipClass(setNewData("Clase de nave", s.getStarshipClass()));
				s.setManufacturer(setNewData("Creador", s.getManufacturer()));
				s.setConsumables(setNewData("Conste en créditos", s.getCostInCredits()));
				s.setLength(setNewData("Tamaño", s.getLength()));
				s.setCrew(setNewData("Tripulación", s.getCrew()));
				s.setPassengers(setNewData("Pasajeros", s.getPassengers()));
				s.setMaxAtmospheringSpeed(setNewData("Máxima velocidad atmosférica", s.getMaxAtmospheringSpeed()));
				s.setHyperdriveRating(setNewData("Radio de rotación", s.getHyperdriveRating()));
				s.setMglt(setNewData("MGLT", s.getMglt()));
				s.setCargoCapacity(setNewData("Capacidad de carga", s.getCargoCapacity()));
				s.setConsumables(setNewData("Consumables", s.getConsumables()));
				s.setCreated(setNewData("Fecha de creación", s.getCreated()));
				s.setEdited(LocalDate.now().toString());
				
				System.out.println("Personajes actuales: ");
				s.getPeople().forEach(pl-> System.out.println("Código: "+pl.getCodigo()+"; Nombre: "+pl.getName()));
				boolean editPeople = FunctionsUtils.getConfirmation("¿Desea añadir más personajes (si/no)?");
				if(editPeople) {
					Set<People> peopleAux = CreateElements.setPeople();
					s.getPeople().addAll(peopleAux);
				}
				
				System.out.println("Películas actuales: ");
				s.getFilms().forEach(f-> System.out.println("Código: "+f.getCodigo()+"; Nombre: "+f.getTitle()));
				boolean editFilms = FunctionsUtils.getConfirmation("¿Desea añadir más películas (si/no)?");
				if(editFilms) { 
					Set<Films> filmsAux = CreateElements.setFilms();
					s.getFilms().addAll(filmsAux);
				}
				
				boolean result = HibernateUtilities.updateElement(s);
				if(result) System.out.println("Registro actualizado.");
				
			}else System.out.println("No se ha encontrado ninguna name con el código "+codigo);
		}
	}
	
	/**
	 * Función auxiliar que permite establecer si el usuario decide editar un campo de un objeto.
	 * @param field Campo a editar
	 * @param actualValue Valor del campo a editar
	 * @return String con el nuevo valor si el usuario decide editar el campo. 
	 */
	private static String setNewData(String field, String actualValue) {
		String newValue = actualValue;
		System.out.println("Introduce el nuevo valor o pulsa intro para mantener el actual.");
		System.out.println(field +" actual: "+actualValue);
		
		if(field.equals("Fecha de nacimiento") ||  field.equals("Creación") || field.equals("Estreno")) {
			boolean editDate = FunctionsUtils.getConfirmation("¿Desea editar la fecha actual? (si/no)?");
			if(editDate) {
				LocalDate newDate = FunctionsUtils.validateLocalDate("Introduzca nueva fecha para "+field+" (dd/mm/yyyy): ");
				newValue = String.valueOf(newDate);
			}
		}else {
			String inputValue = sc.nextLine();
			if(!inputValue.equals("")) {
				newValue = inputValue;
			}
		}
		return newValue;
	}
}
