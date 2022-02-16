package com.fran.starwars;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Collections;

import com.fran.starwars.model.Films;
import com.fran.starwars.model.People;
import com.fran.starwars.model.Planets;
import com.fran.starwars.model.Starships;
import com.fran.starwars.utilities.*;
import com.fran.starwars.menuoptions.*;

/**
 *@author Francisco David Manzanedo
 */
public class App 
{
	private final static String TYPE_PEOPLE = "People";
	private final static String TYPE_PLANETS = "Planets";
	private final static String TYPE_FILMS = "Films";
	private final static String TYPE_STARSHIPS = "Starships";
		
    public static void main( String[] args )
    {
    	boolean exitMainMenu = false, exitSubMenu = false;
    	HibernateUtilities.desactivarLog();
        HibernateUtilities.conectar();
        
        do {
        	MenuUtils.mainMenuOptions();
        	switch (MenuUtils.getOption()) {
        	//Exit
        	case "0": exitMainMenu = true; break;
        	//People Options
			case "1":
				do {
					MenuUtils.subMenuOptionsByType(TYPE_PEOPLE);
					switch(MenuUtils.getOption()) {
						case "0": exitSubMenu = true; break;
						case "1": peopleQueryOptions(); break;
						case "2": CreateElements.addNewPeople(); break;
						case "3": EditElements.editPeople(); break;
						case "4": DeleteElements.deletePeople(); break;
						default: System.out.println("Opción no permitida"); break;
					}
					
				}while(!exitSubMenu);
				break;
			//Planets Options
			case "2":
				do {
					MenuUtils.subMenuOptionsByType(TYPE_PLANETS);
					switch(MenuUtils.getOption()) {
						case "0": exitSubMenu = true; break;
						case "1": planetsQueryOptions(); break;
						case "2": CreateElements.addPlanets(); break;
						case "3": EditElements.editPlanet(); break;
						case "4": DeleteElements.deletePlanet(); break;
						default: System.out.println("Opción no permitida"); break;
					}
					
				}while(!exitSubMenu);
				break;
			//Films Options
			case "3":
				do {
					MenuUtils.subMenuOptionsByType(TYPE_FILMS);
					switch(MenuUtils.getOption()) {
						case "0": exitSubMenu = true; break;
						case "1": filmsQueryOptions(); break;
						case "2": CreateElements.addFilms(); break;
						case "3": EditElements.editFilm(); break;
						case "4": DeleteElements.deleteFilm(); break;
						default: System.out.println("Opción no permitida"); break;
					}
				}while(!exitSubMenu);
				break;
			//Starships Options
			case "4":
				do {
					MenuUtils.subMenuOptionsByType(TYPE_STARSHIPS); 
					switch(MenuUtils.getOption()) {
						case "0": exitSubMenu = true; break;
						case "1": strarshipsQueryOptions(); break;
						case "2": CreateElements.addStarships(); break;
						case "3": EditElements.editStarships(); break;
						case "4": DeleteElements.deleteStarship(); break;
						default: System.out.println("Opción no permitida"); break;	
					}
				}while(!exitSubMenu);
				break;
			//Opciones no peromitidas
			default:System.out.println("Opción no permitada"); break;
        	
			}
			
		} while (!exitMainMenu);
      
        System.out.println("Hasta pronto!\n");
        HibernateUtilities.desconectar();
    }
    


	/**
     * Maneja las diferentes opciones de consultas a realizar sobre people.
     */
    private static void peopleQueryOptions() {
      	boolean exit = false;
      	do {
      		MenuUtils.subQueryMenuOptions(TYPE_PEOPLE);
			switch (MenuUtils.getOption()) {
			// Exit
			case "0": exit = true; break;
			// Buscar personaje por nombre
			case "1":
				String name = FunctionsUtils.validateString("Introduce nombre: ", 100);
				List<People> findPeopleByName = HibernateUtilities
						.findElementByName(name, People.class);
				
				if (findPeopleByName.size() > 0) {
					System.out.println("\nResultados de la búsqueda: ");
					findPeopleByName.forEach(p -> 
						System.out.println("Código: "+p.getCodigo()+"; Name: "+p.getName()));
					System.out.println();
				} else 
					System.out.println("No se han encontrado resultados.");
				
				break;
			// Mostrar personajes sin especie
			case "2":
				List<People> peopleWithoutSpecie = HibernateUtilities.getPeopleWithoutSpecie();
				System.out.println("\nPersonajes sin especie: ");
				if(peopleWithoutSpecie.size() > 0) {
					peopleWithoutSpecie.forEach(p-> 
						System.out.println("Código: "+p.getCodigo()+"; Name: " + p.getName()));
					System.out.println();
				}else
					System.out.println("No se han encontrado resultados.");

				break;
			// Mostrar personaje(s) que han conducido más starships
			case "3":
//				List<People> r1 = HibernateUtilities.getPeopleHaveDrivenMostStarships();
//				r1.forEach(p-> System.out.println("Código: "+p.getCodigo()+"; Name: " + p.getName()));
//					System.out.println();
					List<People> peopleList = HibernateUtilities.devolverClase(People.class);
					Integer max = peopleList.stream()
											.mapToInt(p -> p.getStarships().size())
											.max().orElse(0);
					peopleList.stream()
							  .filter(p -> p.getStarships().size() >= max)
							  .forEach(p -> {
								  System.out.println("Código: "+p.getCodigo()+"; Name: "+p.getName());
								  System.out.println("Starships: ");
								  p.getStarships().forEach(s-> System.out.println(s));
							  });
			  
				break;
			// Mostrar el color de ojos más repetido en personajes
			case "4":
				Map<String, Long> map = HibernateUtilities.getMostCommonPeopleEyeColor();
//				map.forEach((k, v) -> System.out.println((k + ":" + v)));
				Stream<Map.Entry<String,Long>> resultMap =
					    map.entrySet()
					       .stream()
					       .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
					       .limit(1);
				 resultMap.forEach(x-> 
				 	System.out.print("\nColor de ojos "+x.getKey()+" repetido "+x.getValue()+" veces\n"));
				break;
			//Opciones no permitidas
			default: System.out.println("Opción no permitida."); break;
			
			}
      	}while(!exit);
    }
    
    /**
     * Maneja las diferentes opciones de consultas a realizar sobre planets.
     */
    private static void planetsQueryOptions() {
		boolean exit = false;
		do {
			MenuUtils.subQueryMenuOptions(TYPE_PLANETS);
			switch (MenuUtils.getOption()) {
			case "0": exit = true; break;
			// Buscar planetas por nombre
			case "1":
				String name = FunctionsUtils.validateString("Introduce nombre: ", 100);
				List<Planets> resultFindPlanetsByName = HibernateUtilities.findPlanetsByName(name);
				if (resultFindPlanetsByName.size() > 0) {
					resultFindPlanetsByName
							.forEach(p -> System.out.println("Código: " + p.getCodigo() + "; Name: " + p.getName()));
					System.out.println();

				} else
					System.out.println("No se han encontrado resultados.");

				break;
			// Mostrar planetas sin personajes
			case "2":
				List<Planets> resultPlanetsWithoutSpecies = HibernateUtilities.getPlanetsWithoutSpecies();
				if (resultPlanetsWithoutSpecies.size() > 0) {
					resultPlanetsWithoutSpecies
							.forEach(p -> System.out.println("Código: " + p.getCodigo() + "; Name: " + p.getName()));
					System.out.println();

				} else
					System.out.println("No se han encontrado resultados.");

				break;
			// Opciones no peromitidas
			default:
				System.out.println("Opción no permitida.");
				break;
			}
		} while (!exit);
	}
	
	/**
	 * Maneja las diferentes opciones de consultas a realizar sobre starships.
	 */
	private static void strarshipsQueryOptions() {
		String name = FunctionsUtils.validateString("Introduce nombre: ", 100);
		List<Starships> resultStarshipsByName = HibernateUtilities
				.findElementByName(name, Starships.class);
		if(resultStarshipsByName.size() > 0) {
			System.out.println("\nResultados de la búsqueda: ");
			resultStarshipsByName.forEach(p -> 
				System.out.println("Código: "+p.getCodigo()+"; Name: "+p.getName()));
			System.out.println();
		
		} else 
			System.out.println("No se han encontrado resultados.");
	}
	
	/**
	 * Maneja las diferentes opciones de consultas a realizar sobre films.
	 */
	private static void filmsQueryOptions() {
		String title = FunctionsUtils.validateString("Introduce título: ", 100);
		List<Films> resultFindFilmsByTitle = HibernateUtilities.findFilmsByTitle(title);
		if(resultFindFilmsByTitle.size() > 0) {
			System.out.println("\nResultados de la búsqueda: ");
			resultFindFilmsByTitle.forEach(f-> 
				System.out.println("Código: "+f.getCodigo()+"; Título: "+f.getTitle()));
			System.out.println();
		} else 
			System.out.println("No se han encontrado resultados.");	
	}
}