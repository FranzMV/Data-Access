package com.fran.practicatema1.serializeutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.fran.practicatema1.entidades.CumulativeIncidence;

/**
 * 
 * @author Francisco David Manzanedo Valle.
 *
 */
public class SerializeUtils {

	/**
	 * Serializa una lista de objetos de tipo CumulativeIncidence.
	 * @param list lista de objetos de tipo CumulativeIncidence.
	 * @param filename nombre del fichero donde guarda los datos.
	 */
	public static void serializeCumulativeIncidence(List<CumulativeIncidence> list, String filename) {
		
		File file = new File(filename);
		
		try {
			
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream objectsFile = new ObjectOutputStream(fileOut);
			objectsFile.writeObject(list);
			objectsFile.close();
			fileOut.close();
			
		}catch (FileNotFoundException e1) {
			System.err.println("Fichero ".concat(filename).concat(" no encontrado."));
			e1.printStackTrace();
		}catch (IOException e2) {
			System.err.println("Ha ocurrido un error serializando el fichero ".concat(filename));
			e2.printStackTrace();
		}
	}
	
	
	/**
	 * Desserializa una lista de objetos de tipo CumulativeIncidence.
	 * @param filename nombre del fichero a deserializar.
	 * @return lista de objetos de tipo CumulativeIncidence.
	 */
	@SuppressWarnings("unchecked")
	public static List<CumulativeIncidence> deserializeCumulativeIncidence(String filename)  {
		
		List<CumulativeIncidence> result = new ArrayList<CumulativeIncidence>();
		File file = new File(filename);
		
		try {
			
			FileInputStream fileOut = new FileInputStream(file);
			ObjectInputStream objectsFile = new ObjectInputStream(fileOut);
			result = (ArrayList<CumulativeIncidence>)objectsFile.readObject();
			objectsFile.close();
			fileOut.close();
			
		}catch(FileNotFoundException e1) {
			System.err.println("Fichero ".concat(filename).concat(" no encontrado."));
			e1.printStackTrace();
		}catch (ClassNotFoundException e2) {
			System.err.println("Clase a desserializar no encontrada.");
			e2.printStackTrace();
		}catch (IOException e3) {
			System.err.println("Se ha producido un error.");
			e3.printStackTrace();
		}
		
		return result;
	}
}
