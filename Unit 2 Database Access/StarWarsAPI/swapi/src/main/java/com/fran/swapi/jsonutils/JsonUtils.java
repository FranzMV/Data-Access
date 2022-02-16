package com.fran.swapi.jsonutils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fran.swapi.entidades.Films;
import com.fran.swapi.entidades.People;
import com.fran.swapi.entidades.Planets;
import com.fran.swapi.entidades.Species;
import com.fran.swapi.entidades.Starships;
import com.fran.swapi.entidades.Vehicles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**  @author Francisco David Manzanedo */
public class JsonUtils {
	
	private static final String TAG_HOMEWORLD = "homeworld";
	
	/**
	 * Obtiene el contenido de una URL como un String
	 * @param web String that stores the url
	 * @return String with all the json content
	 */
	public static String readUrl(String web) {
		
		try {
			URL url = new URL(web);
			URLConnection uc = url.openConnection();
			uc.setRequestProperty("User-Agent", "PostmanRuntime/7.20.1");
			uc.connect();
			
			String lines = new BufferedReader(new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8))
					.lines()
					.collect(Collectors.joining());
			return lines;
		} catch (Exception e) {  System.out.println("No se ha podido leer la URL: " + web);  }
		
		return null;
	}
		
	/**
	 * Obtiene los Json's y los a objetos dependiendo del tipo.
	 * @param <T> Genérico
	 * @param classType La clase que queremos parsear
	 * @param urlBase String correspondiente a la url donde se encuentra el json
	 * @param numElements Long número de elementos a parsear.
	 * @param finalUrl String con el formato al final de la url (/?format=json)
	 * @return Lista genérica que depende del tipo de clase que le pasemos como parámetro.
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getJson(Class<T> classType, String urlBase, long numElements, String finalUrl) {
    	String jsonString;
    	T elemenT;
    	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    	List<T> result = new ArrayList<T>();
    	JsonParser parser;
    	JsonObject obj;
    	String homeworld ="";
    
    	for(int i = 1; i<=numElements; i++) {
    		jsonString = readUrl(urlBase+i+finalUrl);
    		if(jsonString != null) {
    			parser = new JsonParser();
				obj = parser.parse(jsonString).getAsJsonObject();
//				JsonObject jsonObject = (JsonObject)obj.get("result");
//				JsonObject jObject = (JsonObject)jsonObject.get("properties");
			
    			//People
    			if(classType.getName() == (People.class.getName())) {
    				
    				homeworld = obj.get(TAG_HOMEWORLD).getAsString();
    				elemenT = gson.fromJson(obj, classType);
    				    				
    				People p = new People();
    				p = (People) elemenT;
    				p.setHomeworld(Integer.parseInt(homeworld.split("/")[5]));
    				p.setCodigo(i);
    				result.add((T) p);
    				
    			//Species	
    			}else if(classType.getName() == (Species.class.getName())) {
    				
    				homeworld = obj.get(TAG_HOMEWORLD).toString();
					elemenT = gson.fromJson(obj, classType);
					
					Species s = new Species();
					s = (Species) elemenT;
					if(!homeworld.equals("null")) {
						s.setHomeworld(Integer.parseInt(homeworld.split("/")[5]));
						s.setCodigo(i);
						result.add((T) s);
					}else {
						s.setHomeworld(null);
						s.setCodigo(i);
						result.add((T) s);
					}
				//Vehicles
    			}else if(classType.getName() == (Vehicles.class.getName())){
    				elemenT = gson.fromJson(obj, classType);
    				Vehicles v = new Vehicles();
    				v = (Vehicles) elemenT;
    				v.setCodigo(i);
    				result.add((T)v);
    				
    			//Planets
    			}else if(classType.getName() == (Planets.class.getName())) {
    				elemenT = gson.fromJson(obj, classType);
    				Planets p = new Planets();
    				p = (Planets)elemenT;
    				p.setCodigo(i);
    				result.add((T) p);
    				
    			//Films
    			}else if(classType.getName() == (Films.class.getName())) {
    				elemenT = gson.fromJson(obj, classType);
					Films f = new Films();
					f = (Films) elemenT;
					f.setCodigo(i);
					result.add((T) f);
					
    			//Starships
    			}else if(classType.getName() == (Starships.class.getName())) {
    				elemenT = gson.fromJson(obj, classType);
    				Starships s = new Starships();
    				s = (Starships) elemenT;
    				s.setCodigo(i);
    				result.add((T)s);
    			}
    		}
    	}
    	return result;
    }
	
	
	/**
	 * Obtiene una sublista (en la BD corresponde a la relación M:M
	 * @param urlBase String correspondiente a la url donde se encuentra el json
	 * @param finalUrl String con el formato al final de la url (/?format=json)
	 * @param elementList String representing the element to get
	 * @param numElements Long número de elementos a parsear.
	 * @return Map <<Long> List<long>> índice de la tabla principal como clave y una lista con los índices de los
	 * elementos asociados.
	 */
	public static Map<Long, List<Long>> getSubList(String urlBase, String finalUrl, String elementList, 
			long numElements) {
		
		Map<Long, List<Long>> result = new HashMap<Long, List<Long>>();
		String jsonString;
		JsonParser parser;
    	JsonObject obj;
    	JsonArray jsonArray;
		
		for(int i = 1; i<=numElements; i++) {
			jsonString = readUrl(urlBase+i+finalUrl);
			if(jsonString != null) {
				parser = new JsonParser();
				obj = parser.parse(jsonString).getAsJsonObject();
				jsonArray = (JsonArray)obj.getAsJsonArray(elementList);
				
				if(result.get((long)i) == null) 
					result.put((long)i, new ArrayList<Long>());
				
				for(Object object: jsonArray) 
					result.get((long)i).add(Long.parseLong(object.toString().split("/")[5]));
			}
		}
		//result.entrySet().stream().forEach(e-> System.out.println(e.getKey()+": "+e.getValue()));
		return result;
	}
	
	
	/**
	 * Obtiene el número de elementos que cada Json.
	 * @param url String correspondiente al Json.
	 * @param name String correspondiente la etiqueta que contiene el númnero de elementos (count).
	 * @return long correspondiente al número de elementos.
	 */
	public static long getNumberOfElements(String url, String name) {
		
		long result;
		
		try {
			Object obj = new JSONParser().parse(readUrl(url));
			JSONObject jsonObject = (JSONObject)obj;
			result = (long)jsonObject.get(name);
			return result;
			
		} catch (ParseException e) {
			System.out.println("Ha ocurrido un error leyendo el json: "+url);
			return -1;
		}
	}
	
}
