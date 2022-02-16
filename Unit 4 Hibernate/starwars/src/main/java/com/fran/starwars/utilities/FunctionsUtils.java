package com.fran.starwars.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * @author Francisco David Manzanedo
 */
public class FunctionsUtils {
	
	private static Scanner sc = new Scanner(System.in);
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	//private static DateTimeFormatter formatterOut = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
	
	/**
	 * Valida que una cadena de texto no esté vacía
	 * @param msg String correspondiente al mensaje a mostrar al usuario
	 * @param stringLength int tamaño máximo de la cadena.
	 * @return
	 */
	public static String validateString(String msg, int stringLength) {
		String result;
		System.out.print(msg);
		do {
			result = sc.nextLine();
			if(result.isEmpty() || result.isBlank()) {
				System.out.println("El campo no puede quedar vacío");
				System.out.println(msg);
			}
			
			if(result.length() > stringLength) {
				System.out.println("El dato a introducir no puede contener más de: "+stringLength+" caracteres.");
				System.out.println(msg);
			}
		} while (result.isEmpty() || result.isBlank() || result.length() > stringLength);
		
		return result;
	}
	

	/**
	 * Valida un fecha con formato dd-MM-yyyy
	 * @param msg String correspondiente al mensaje a mostrar al usuario
	 * @return Objeto LocalDate.
	 */
	public static LocalDate validateLocalDate(String msg){
    	
    	LocalDate date = LocalDate.now();
    	boolean dateOK = false;
    	String dateString;
    	System.out.println(msg);
    	do {
    		dateString = sc.nextLine();
	    	try {
				date = LocalDate.parse(dateString, formatter);
				dateOK = true;
			} catch (DateTimeParseException e) {
				System.out.println("Debe introducir un formato correcto: (dd-MM-yyyy).");
				System.out.println(msg);
				dateOK = false;
			}
    	}while(!dateOK);
    	return date;
    }
	
	/**
	 * Solicita al usuario una confirmación para realizar una determinada acción
	 * @param msg String mensaje a mostrar al usuario.
	 * @return boolean a true si confirma la acción, false si la cancela.
	 */
	public static boolean getConfirmation(String msg) {
		System.out.print(msg);
		String response = sc.nextLine();
		boolean result = false;
		boolean validResponse = false;
		do {
			if(response.equals("si") || response.equals("Si")) {
				result = true;
				validResponse = true;
			}else if(response.equals("no") || response.equals("NO")) {
				validResponse = true;
				result = false;
			}else {
				validResponse = false;
				System.out.print("La respuesta no es válida. Seleccione \"sí\" para confirmar o \"no\" para cancelar: ");
				response = sc.nextLine();
			}
		} while (!validResponse);	
		return result;
	}
}	


