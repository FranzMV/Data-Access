package com.fran.starwars.utilities;

import java.util.Scanner;

/**
 * @author Francisco David Manzanedo
 */

public class MenuUtils {

	private static Scanner sc = new Scanner(System.in);
	private final static String TYPE_PEOPLE = "People";
	private final static String TYPE_PLANETS = "Planets";
	private final static String TYPE_FILMS = "Films";
	private final static String TYPE_STARSHIPS = "Starships";
	
  public static void mainMenuOptions() {
        System.out.println("\nMENÚ PRINCIPAL");
        System.out.println("========================");
        System.out.println("1. Gestión de people");
        System.out.println("2. Gestión de planets");
        System.out.println("3. Gestión de films");
        System.out.println("4. Gestión de starships");
        System.out.println("0. Salir");
        System.out.print("\nEscoja una opción: ");
    }
  
  public static void subMenuOptionsByType(String type) {
	  System.out.println("\nMENÚ "+type.toUpperCase());
	  System.out.println("============================");
      System.out.println("1. Consultas "+type);
      System.out.println("2. Insertar " + type);
      System.out.println("3. Modificar " + type);
      System.out.println("4. Borrar " + type);
      System.out.println("0. Volver al menú principal.");
      System.out.print("\nEscoja una opción: ");
  }
  
  public static void subQueryMenuOptions(String type) {
	  
		switch (type) {
		case TYPE_PEOPLE:
			System.out.println("\nCONSULTAS "+type.toUpperCase());
			System.out.println("=================================");
			System.out.println("1. Buscar personajes por nombre.");
			System.out.println("2. Ver personajes sin specie.");
			System.out.println("3. Personaje(s) que han conducido más starships.");
			System.out.println("4. Color de ojos más repetido en personajes.");
			System.out.println("0. Volver al menú "+type);
			System.out.print("\nEscoja una opción: ");
			break;

		case TYPE_PLANETS:
			System.out.println("\nCONSULTAS "+type.toUpperCase());
			System.out.println("=================================");
			System.out.println("1. Buscar planetas por nombre.");
			System.out.println("2. Ver planetas sin ningún personaje.");
			System.out.println("0. Volver al menú "+type);
			System.out.print("\nEscoja una opción: ");
			break;

		case TYPE_FILMS:
			System.out.println("\nCONSULTAS "+type.toUpperCase());
			System.out.println("=================================");
			System.out.println("1. Buscar film por título.");
			System.out.println("0. Volver al menú "+type);
			System.out.print("\nEscoja una opción: ");
			break;
		
		case TYPE_STARSHIPS:
			System.out.println("\nCONSULTAS "+type.toUpperCase());
			System.out.println("=================================");
			System.out.println("1. Buscar starship por nombre.");
			System.out.println("0. Volver al menú "+type);
			System.out.print("\nEscoja una opción: ");
			break;
			
		default:
			System.out.println("Opción no permitida.");
			break;
		}
  }
  
  
  public static String getOption() {
	  String option = sc.nextLine();
	  return option;
  }
}
