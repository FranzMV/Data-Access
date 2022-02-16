package com.fran.practicatema1.jsonutils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fran.practicatema1.entidades.EuskadiCommunity;
import com.fran.practicatema1.entidades.NewPcrPositiveByGenre;
import com.fran.practicatema1.entidades.TotalPcrsByDate;

/**
 * @author Francisco David Manzanedo Valle.
 */
public class JsonUtils {
	
	private static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	/**
	 * Recibe una url en formato String y devuelve el contenido de esa Url como una
	 * cadena
	 * @param web la cadena que almacena la url
	 * @return Todo el contenido de esa url en un String
	 */
	public static String readUrl(String web) {
		try {
			URL url = new URL(web);
			URLConnection uc = url.openConnection();
			uc.setRequestProperty("User-Agent", "PostmanRuntime/7.20.1");
			uc.connect();
			String lines = new BufferedReader(new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8))
					.lines().collect(Collectors.joining());
			
			return lines;
			
		} catch (Exception e) { System.out.println("No se ha podido la leer la URL: " + web); }
		
		return null;
	}
	
	
	/**
	 * Obtiene las pruebas Pcr realizadas en determinadas fechas.
	 * @param urlCadena Todo el contenido de esa url en un String
	 * @return Lista de Tipo TestPcrPorFecha
	 */
	public static List<TotalPcrsByDate> getPcrsPerformedByDate(String urlString) {
		
		List<TotalPcrsByDate> result = new ArrayList<TotalPcrsByDate>();
		
		try {
			
			Object obj = new JSONParser().parse(readUrl(urlString));
			JSONObject jsonObject = (JSONObject)obj;
			//Quitar
			//String lastUpdate = (String)jsonObject.get("lastUpdateDate");	
			JSONArray jsonArray= (JSONArray) jsonObject.get("byDate");	
			
			for (Object object : jsonArray) {
				
				String date = (String) (((JSONObject)object).get("date"));
				long value = (long) (((JSONObject)object).get("pcrTestCount"));
				
				LocalDate dateAux = clearDateString(date);
				
				result.add(new TotalPcrsByDate(dateAux, value));
			}
		
		} catch (ParseException e) {
			System.out.println("Ha ocurrido un error.");
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * Obtiene los campos date, menCount y womenCount 
	 * @param urlString cade de la URL de donde obtener los datos.
	 * @return Lista de tipo NewPcrPositiveByGenre.
	 */
	public static List<NewPcrPositiveByGenre> getNewPcrPositiveByGenres(String urlString){
		
		List<NewPcrPositiveByGenre> result = new ArrayList<NewPcrPositiveByGenre>();
		
		try {
			
			Object obj = new JSONParser().parse(readUrl(urlString));
			JSONObject jsonObject = (JSONObject)obj;
			JSONArray jsonArray= (JSONArray)jsonObject.get("byDate");	
			
			for (Object object : jsonArray) {
				
				String date = (String) (((JSONObject)object).get("date"));
				long menCount = (long) (((JSONObject)object).get("menCount"));
				long womenCount = (long) (((JSONObject)object).get("womenCount"));
				
				LocalDate dateAux = clearDateString(date);
				result.add(new NewPcrPositiveByGenre(dateAux, menCount, womenCount));
			}
		
		} catch (ParseException e) {
			System.out.println("Ha ocurrido un error.");
			e.printStackTrace();
		}

		return result;
	}
	
	
	
	public static void otherSourceAPI(String urlString) {
		
		
		//String communityDate = "";
		long communityTodayDeads = 0;
		long communityTodayNewCases = 0;
		
		List<EuskadiCommunity> result = new ArrayList<EuskadiCommunity>();

		try {
			Object obj = new JSONParser().parse(readUrl("https://api.covid19tracking.narrativa.com/api/2020-03-10"));
			JSONObject jsonObject = (JSONObject)obj;
			JSONObject dates = (JSONObject)jsonObject.get("dates");
			JSONObject auxDate = (JSONObject)dates.get("2020-03-10");
			JSONObject country = (JSONObject)auxDate.get("countries");
			JSONObject spain = (JSONObject)country.get("Spain");
			
			JSONArray ja = (JSONArray)spain.get("regions");
			
			
			
			for (Object object : ja) {
				JSONObject jsonOb = (JSONObject)object;
				String community = (String) (((JSONObject)object).get("name"));
				System.out.println(community);
				JSONArray jsonArray = ((JSONArray)jsonOb.get("sub_regions"));
				for(Object object2: jsonArray) {
					String cityName = (String) (((JSONObject)object2).get("name"));
					if(cityName.equals("Bizkaia")) {
						communityTodayDeads = (long) (((JSONObject)object2).get("today_deaths"));
						communityTodayNewCases = (long) (((JSONObject)object2).get("today_confirmed"));
						System.out.println("Bizkaia: "+communityTodayDeads);
						System.out.println("Bizkaia: "+communityTodayNewCases);
					
					}	
				}	
			}
				

		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	/**
	 * Limpia una fecha recibida como String y le da el formato yyyy-MM-dd
	 * @param date LocalDate en formato yyyy-MM-dd
	 * @return String date
	 */
	private static LocalDate clearDateString(String date) {
		
		String result = date.substring(0,10).replace("'", "").trim();
		return LocalDate.parse(result,inputFormatter);
	}
}
