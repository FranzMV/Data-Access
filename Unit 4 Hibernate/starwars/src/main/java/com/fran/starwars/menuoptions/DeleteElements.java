package com.fran.starwars.menuoptions;

import java.util.List;
import java.util.Optional;

import com.fran.starwars.model.Films;
import com.fran.starwars.model.People;
import com.fran.starwars.model.Planets;
import com.fran.starwars.model.Starships;
import com.fran.starwars.utilities.FunctionsUtils;
import com.fran.starwars.utilities.HibernateUtilities;

/**
 * @author Francisco David Manzanedo
 */
public class DeleteElements {
	
	public static void deletePeople() {
		List<People> people = HibernateUtilities.devolverClase(People.class);
		System.out.println();
		people.forEach(p-> System.out.println("Código: "+p.getCodigo()+"; "+p.getName()));
		String codigo = FunctionsUtils.validateString("\nSeleccione el código del personaje que desea eliminar: ", 3);
		Optional<People> peopleFound = people.stream().filter(p-> p.getCodigo() == Integer.parseInt(codigo)).findFirst();
		People p = peopleFound.orElse(null);
		if(p != null) {
			System.out.println("¿Realmente desea eliminar al personaje: ");
			System.out.println(p.getName()+"(Código: "+p.getCodigo()+")"
			+"; "+"Planeta: "+p.getPlanets().getName()+" con : "+p.getSpecies().size()+" especies asociadas;"
					+p.getStarships().size()+" naves asociadas; "+p.getVehicles().size()+" vehículos asiciados; " 
					+p.getFilms().size()+" películas asociadas?");
			boolean confirmation = FunctionsUtils.getConfirmation("Indique \"sí\" para confirmar o \"no\" para cancelar: ");
			if(confirmation) {
				boolean delete = HibernateUtilities.delete(People.class,"WHERE codigo ="+ String.valueOf(p.getCodigo()));
				if(delete) System.out.println("Personaje eliminado.");
			}
			else System.out.println("No se ha eliminado ningún registro.");
		}
		else 
			System.out.println("Personaje no encontrado.");
	}
	
	
	
	public static void deleteFilm() {
		List<Films> films = HibernateUtilities.devolverClase(Films.class);
		System.out.println();
		films.forEach(f-> System.out.println("Código: "+f.getCodigo()+"; "+f.getTitle()));
		String codigo = FunctionsUtils.validateString("\nSeleccione el código del personaje que desea eliminar: ", 3);
		Optional<Films> filmFound = films.stream().filter(f-> f.getCodigo() == Integer.parseInt(codigo)).findFirst();
		Films f = filmFound.orElse(null);
		if(f != null) {
			System.out.println("¿Realmente desea eliminar la película: ");
			System.out.println(f.getTitle()+"(Código: "+f.getCodigo()+") con: "
					+f.getPeople().size()+" personajes asociados; "
					+f.getStarships().size()+" naves asociadas; "
					+f.getVehicles().size()+" vehículos asiciados y " 
					+f.getPlanets().size()+" planetas asociados?");
			boolean confirmation = FunctionsUtils.getConfirmation("Indique \"sí\" para confirmar o \"no\" para cancelar: ");
			if(confirmation) {
				boolean delete = HibernateUtilities.delete(Films.class, "WHERE codigo="+f.getCodigo());
				if(delete) System.out.println("Film eliminado.");
			}
			else System.out.println("No se ha eliminado ningún registro.");
		}
		else System.out.println("Film no encontrado.");
	}
	
	
	public static void deletePlanet() {
		List<Planets> planets = HibernateUtilities.devolverClase(Planets.class);
		System.out.println();
		planets.forEach(p-> System.out.println("Código: "+p.getCodigo()+"; "+p.getName()));
		String codigo= FunctionsUtils.validateString("Seleccione el código del planeta que desea eleiminar: ", 3);
		Optional<Planets> planetFound = planets.stream().filter(p-> p.getCodigo() == Integer.parseInt(codigo)).findFirst();
		Planets p = planetFound.orElse(null);
		if(p != null) {
			System.out.println("¿Realmente desea eliminar el planeta: ");
			System.out.println(p.getName()+"(Código: "+p.getCodigo()+") con : "
					+p.getSpecies().size()+" especies asociadas;"
					+p.getPeople().size()+" personajes asociados y "
					+p.getFilms().size()+" películas asociadas?");
			boolean confirmation = FunctionsUtils.getConfirmation("Indique \"sí\" para confirmar o \"no\" para cancelar: ");
			if(confirmation) {
				boolean delete = HibernateUtilities.delete(Planets.class, "WHERE codigo ="+p.getCodigo());
				if(delete) System.out.println("Planeta eliminado.");
			}
			else System.out.println("No se ha eliminado ningún registro.");
			
		}else
			System.out.println("Planeta no encontrado.");
	}
	

	
	public static void deleteStarship() {
		List<Starships> starships = HibernateUtilities.devolverClase(Starships.class);
		System.out.println();
		starships.forEach(s-> System.out.println("Código: "+s.getCodigo()+"; "+s.getName()));
		String codigo= FunctionsUtils.validateString("Seleccione el código de la nave que desea eleiminar: ", 3);
		Optional<Starships> starshipFound = starships.stream().filter(s-> s.getCodigo() == Integer.parseInt(codigo)).findFirst();
		Starships s = starshipFound.orElse(null);
		if(s != null) {
			System.out.println("¿Realmente desea eliminar la nave: ");
			System.out.println(s.getName()+"(Código: "+s.getCodigo()+") con : "
					+s.getPeople().size()+" personajes asociados y "
					+s.getFilms().size()+" películas asociadas?");
			boolean confirmation = FunctionsUtils.getConfirmation("Indique \"sí\" para confirmar o \"no\" para cancelar: ");
			if(confirmation) {
				boolean delete = HibernateUtilities.delete(Starships.class, "WHERE codigo="+s.getCodigo());
				if(delete) System.out.println("Eliminado.");
				
			}else System.out.println("No se ha eliminado ningún registro.");
			
		}else System.out.println("Nave no encontrada.");
	}
}
