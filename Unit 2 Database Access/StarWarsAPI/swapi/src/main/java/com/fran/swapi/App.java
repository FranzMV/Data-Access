package com.fran.swapi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fran.swapi.dbhelper.DBHelper;
import com.fran.swapi.entidades.Films;
import com.fran.swapi.entidades.People;
import com.fran.swapi.entidades.Planets;
import com.fran.swapi.entidades.Species;
import com.fran.swapi.entidades.Starships;
import com.fran.swapi.entidades.Vehicles;
import com.fran.swapi.jdbcutils.JdbcUtils;
import com.fran.swapi.jsonutils.JsonUtils;

import java.util.Scanner;

/**  @author Francisco David Manzanedo */
public class App 
{
	
	private static final String URL = "jdbc:postgresql://localhost:5432/swapi";
	private static final String USER ="postgres";
	private static final String PASS ="postgres";
	
	private static final String URL_PEOPLE ="https://swapi.dev/api/people/";
	private static final String URL_VEHICLES ="https://swapi.dev/api/vehicles/";
	private static final String URL_SPECIES ="https://swapi.dev/api/species/";
	private static final String URL_PLANETS ="https://swapi.dev/api/planets/";
	private static final String URL_FILMS ="https://swapi.dev/api/films/";
	private static final String URL_STARSHIPS="https://swapi.dev/api/starships/";
	private static final String FORMAT_URL="/?format=json";
	
	private static final int MAX_FILMS = 6;
	private static final int MAX_PEOPLE = 83;
	private static final int MAX_PLANETS = 60;
	private static final int MAX_SPECIES = 37;
	private static final int MAX_VEHICLES = 73;
	private static final int MAX_STARSHIPS = 70;
	
	private static Scanner sc = new Scanner(System.in);
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
//	private static final String COUNT_OF_ELEMENTS = "count" Nueva API: "total_records";
	private static final String TAG_CHARACTERS ="characters";
	private static final String TAG_PLANETS ="planets";
	private static final String TAG_FILMS = "films";
	private static final String TAG_PEOPLE = "people";
	private static final String TAG_PILOTS = "pilots";
	
	private static final String SQL_INSERT_PLANETS="INSERT INTO planets "
			+ "(codigo, name, diameter, rotation_period, orbital_period, gravity, population,"
			+ "climate, terrain, surface_water, created, edited) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
	
	private static final String SQL_INSERT_FILMS ="INSERT INTO films "
			+ "(codigo, title, episode_id, opening_crawl, director, "
			+ "producer, release_date, created, edited) "
			+ "VALUES(?,?,?,?,?,?,?,?,?);";
	
	private static final String SQL_INSERT_VEHICLES ="INSERT INTO vehicles "
			+ "(codigo, name, model, vehicle_class, manufacturer, length, "
			+ "cost_in_credits, crew, passengers, max_atmosphering_speed,"
			+ "cargo_capacity, consumables, created, edited) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	
	private static final String SQL_INSERT_STARSHIPS="INSERT INTO starships "
			+ "(codigo, name, model, starship_class, manufacturer, cost_in_credits,"
			+ " length, crew, passengers, max_atmosphering_speed, hyperdrive_rating,"
			+ " MGLT, cargo_capacity, consumables, created, edited) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	
	private static final String SQL_INSERT_SPECIES = "INSERT INTO species "
			+ "(codigo, name, classification, designation, average_height, average_lifespan,"
			+ " eye_color, hair_color, skin_color, language, homeworld, created, edited) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
	
	private static final String SQL_INSERT_PEOPLE = "INSERT INTO people "
			+ "(codigo, name, birth_year, eye_color, gender, hair_color, height, mass, "
			+ "skin_color, homeworld, created, edited) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
	
	private static final String SQL_INSERT_FILMS_PEPOPLE="INSERT INTO films_people"
			+ " (codigo_film, codigo_people) "
			+ "VALUES(?,?);";
	
	private static final String SQL_INSERT_FILMS_PLANETS ="INSERT INTO films_planets"
			+ " (codigo_film, codigo_planet) "
			+ "VALUES(?,?);";
	
	private static final String SQL_INSERT_SPECIES_PEOPLE ="INSERT INTO species_people"
			+ " (codigo_specie, codigo_people) "
			+ "VALUES(?,?);";
	
	private static final String SQL_INSERT_STARSHIPS_FILMS="INSERT INTO starships_films"
			+ " (codigo_starship, codigo_film) "
			+ "VALUES(?,?);";
	
	private static final String SQL_INSERT_STARSHIPS_PEOPLE ="INSERT INTO starships_people"
			+ " (codigo_starship, codigo_people) "
			+ "VALUES(?,?);";
	
	private static final String SQL_INSERT_VEHICLE_FILMS ="INSERT INTO vehicles_films"
			+ " (codigo_vehicle, codigo_film) "
			+ "VALUES(?,?);";
	
	private static final String SQL_INSERT_VEHICLE_PEOPLE ="INSERT INTO vehicles_people"
			+ " (codigo_vehicle, codigo_people) "
			+ "VALUES(?,?);";
	
	private static final String SQL_SELECT_PEOPLE = "SELECT codigo, name FROM people;";
	
	private static final String SQL_SELECT_PLANETS = "SELECT codigo, name FROM planets;";
	
	private static List<People> peopleList = new ArrayList<People>();
	private static List<Films> filmsList = new ArrayList<Films>();
	private static List<Planets> planetsList = new ArrayList<Planets>();
	private static List<Species> speciesList = new ArrayList<Species>();
	private static List<Starships> starshipsList = new ArrayList<Starships>();
	private static List<Vehicles> vehiclesList = new ArrayList<Vehicles>();
	
	private static Map<Long, List<Long>> films_PeopleMap = new HashMap<Long, List<Long>>();
	private static Map<Long, List<Long>> films_PlanetsMap = new HashMap<Long, List<Long>>();
	private static Map<Long, List<Long>> starships_FilmsMap = new HashMap<Long, List<Long>>();
	private static Map<Long, List<Long>> vehicle_FilmsMap = new HashMap<Long, List<Long>>();
	private static Map<Long, List<Long>> species_PeopleMap = new HashMap<Long, List<Long>>();
	private static Map<Long, List<Long>> starships_PeopleMap = new HashMap<Long, List<Long>>();
	private static Map<Long, List<Long>> vehicle_PeopleMap = new HashMap<Long, List<Long>>();	
	
    public static void main( String[] args )
    {
    	
    	String option;
    	JdbcUtils.connectDB(URL, USER, PASS);
    	//setUpDataBase();
    	
    	do {
    		System.out.println("\n1.- Resetear BBDD.");
    		System.out.println("2.- A??adir nuevo film (Statement).");
    		System.out.println("3.- A??adir nuevo film (PreparedStatement).");
    		System.out.println("4.- Buscar pel??cula.");
    		System.out.println("5.- Mostrar personajes sin starship.");
    		System.out.println("0.- Salir.");
    		System.out.print("\nEscoja una opci??n: ");
    		option = sc.nextLine();
    		
    		switch (option) {
    		//Salir
    		case "0":  System.out.println("Hasta pronto!"); break;
    		
    		//Resetear base de datos
			case "1": setUpDataBase(); break;
			
			//A??adir un nuevo Film con Statement		
			case "2": createNewFilmStatement(); break;
				
			//A??adir un nuevo Film con preparedStatement			
			case "3": createNewFilmPreparedStatement(); break;
				
			//Buscar pel??cula por t??tulo
			case "4": showFilmsByTitle(); break;
				
			//Obtener personajes sin starships
			case "5": showPeopleWithoutStarships(); break;
				
			//Opciones no permitidas
			default: System.out.println("Opci??n no permitida."); break;
			
			}
			
		} while (!option.equals("0"));
    	
    	JdbcUtils.disconnetDB();
    }
    
    /** 
     * Borra los datos existentes en las tablas de la base de datos
     * Obtiene los datos de la API y los carga de nuevo en las tablas correspondientes.
     */
    private static void setUpDataBase() {
    	
    	List<Object> tableNames = DBHelper.getTableNames();
	    int resultDropTables = JdbcUtils.dropTables(tableNames);
	    System.out.println(resultDropTables+" tablas eliminadas.");
	    List<Object> createTables = DBHelper.createTables();
	    int resultCreateTables = JdbcUtils.createTables(createTables);
	    System.out.println(resultCreateTables+" tablas creadas.");
 	
//		long numberOfFilms = JsonUtils.getNumberOfElements(URL_FILMS+FORMAT_URL, COUNT_OF_ELEMENTS);
	   	filmsList = JsonUtils.getJson(Films.class, URL_FILMS, MAX_FILMS, FORMAT_URL);
		int resultFilms = JdbcUtils.insertFilms(SQL_INSERT_FILMS, filmsList);

//		long numberOfVehicles = JsonUtils.getNumberOfElements(URL_VEHICLES+FORMAT_URL, COUNT_OF_ELEMENTS);
	    vehiclesList = JsonUtils.getJson(Vehicles.class, URL_VEHICLES, MAX_VEHICLES, FORMAT_URL);
		int resultVehicles = JdbcUtils.insertVehicles(SQL_INSERT_VEHICLES, vehiclesList);
		
		vehicle_FilmsMap = JsonUtils.getSubList(URL_VEHICLES, FORMAT_URL, TAG_FILMS, MAX_VEHICLES);
		int resultVehicle_Films = JdbcUtils.insertManyToManyTables(SQL_INSERT_VEHICLE_FILMS, vehicle_FilmsMap);

//		long numberOfStarships = JsonUtils.getNumberOfElements(URL_STARSHIPS+FORMAT_URL, COUNT_OF_ELEMENTS);
		starshipsList = JsonUtils.getJson(Starships.class, URL_STARSHIPS, MAX_STARSHIPS, FORMAT_URL);
		int resultStarships = JdbcUtils.insertStarships(SQL_INSERT_STARSHIPS, starshipsList);
       
		starships_FilmsMap = JsonUtils.getSubList(URL_STARSHIPS, FORMAT_URL, TAG_FILMS, MAX_STARSHIPS);
		int resultStarships_Films = JdbcUtils.insertManyToManyTables(SQL_INSERT_STARSHIPS_FILMS, starships_FilmsMap);
 	 	
//   	long numberOfPlanets = JsonUtils.getNumberOfElements(URL_PLANETS+FORMAT_URL, COUNT_OF_ELEMENTS);
		planetsList = JsonUtils.getJson(Planets.class, URL_PLANETS, MAX_PLANETS, FORMAT_URL);
		int resultPlanets = JdbcUtils.insertPlanets(SQL_INSERT_PLANETS, planetsList);
   	 	
//  	long numberOfPeople = JsonUtils.getNumberOfElements(URL_PEOPLE+FORMAT_URL, COUNT_OF_ELEMENTS);
	    peopleList = JsonUtils.getJson(People.class, URL_PEOPLE, MAX_PEOPLE, FORMAT_URL);
	    int resultPeople = JdbcUtils.insertPeople(SQL_INSERT_PEOPLE, peopleList);
   	 	
	    films_PeopleMap= JsonUtils.getSubList(URL_FILMS,FORMAT_URL,TAG_CHARACTERS, MAX_FILMS);
	    int resultFilms_People = JdbcUtils.insertManyToManyTables(SQL_INSERT_FILMS_PEPOPLE, films_PeopleMap);
   	 
	    vehicle_PeopleMap = JsonUtils.getSubList(URL_VEHICLES, FORMAT_URL, TAG_PILOTS, MAX_VEHICLES);
    	int resulVehicle_People =JdbcUtils.insertManyToManyTables(SQL_INSERT_VEHICLE_PEOPLE, vehicle_PeopleMap);
    
 		starships_PeopleMap = JsonUtils.getSubList(URL_STARSHIPS, FORMAT_URL, TAG_PILOTS, MAX_STARSHIPS);
 		int resultStarships_People = JdbcUtils.insertManyToManyTables(SQL_INSERT_STARSHIPS_PEOPLE, starships_PeopleMap);
		
//    	long numberOfSpecies = JsonUtils.getNumberOfElements(URL_SPECIES+FORMAT_URL, COUNT_OF_ELEMENTS);
    	speciesList = JsonUtils.getJson(Species.class, URL_SPECIES, MAX_SPECIES, FORMAT_URL);
    	int resultSpecies = JdbcUtils.insertSpecies(SQL_INSERT_SPECIES, speciesList);

    	species_PeopleMap = JsonUtils.getSubList(URL_SPECIES, FORMAT_URL, TAG_PEOPLE, MAX_SPECIES);
    	int resultSpecies_People = JdbcUtils.insertManyToManyTables(SQL_INSERT_SPECIES_PEOPLE, species_PeopleMap);
   	 	
    	films_PlanetsMap = JsonUtils.getSubList(URL_FILMS, FORMAT_URL, TAG_PLANETS , MAX_FILMS);
    	int resultFilms_Planets = JdbcUtils.insertManyToManyTables(SQL_INSERT_FILMS_PLANETS, films_PlanetsMap);
   	 	
    	System.out.println("\n\nRegistros a??adidos en Films: "+resultFilms);
    	System.out.println("Registros a??adidos en Vehicles: "+resultVehicles);
    	System.out.println("Registros a??adidos en Starships: "+resultStarships);
    	System.out.println("Registros a??adidos en Planets: "+resultPlanets);
		System.out.println("Registros a??adidos en People: "+resultPeople);
		System.out.println("Registros a??adidos en Species: "+resultSpecies);
 		System.out.println("Registros a??adidos en Vehicles_Films: "+resultVehicle_Films);
		System.out.println("Registros a??adidos en Starships_Films: "+resultStarships_Films);
 		System.out.println("Registros a??adidos en Films_People: "+resultFilms_People);
 		System.out.println("Registros a??adidos en Vehicles_People: "+resulVehicle_People);
 		System.out.println("Registros a??adidos en Starships_People: "+resultStarships_People);
 		System.out.println("Registros a??adidos en Species_People: "+resultSpecies_People);
 		System.out.println("Registros a??adidos en Planets_Films: "+resultFilms_Planets);
    }
    
    /**
     * Crea un nuevo Film utilizando Statement.
     */
    private static void createNewFilmStatement() {
    	Films newFilm = createNewFilmAux();
    	int resultFilm, resultFilmsPeople, resultFilmsPlanets;
    	
    	showCodigoAndName(SQL_SELECT_PEOPLE);
    	List<Long> cod_people_list = getCodigosFromTable("\nSeleccione el c??digo del personaje  que aparece en el nuevo film y puslse \"intro\""
				+ " para seguir a??adiendo m??s ( o pulse \"0\" para terminar): ");
    	
    	showCodigoAndName(SQL_SELECT_PLANETS);
		List<Long> cod_planet_list = getCodigosFromTable("\nSeleccione el c??digo del planeta que aparece en el nuevo film y puslse \"intro\""
				+ " para seguir a??adiendo m??s ( o pulse \"0\" para terminar): ");
		
		if(confirmation()) {
			resultFilm = JdbcUtils.insertNewFilmStatement(newFilm);
			resultFilmsPeople = JdbcUtils.insertManyToManyTableStatement("films_people", newFilm.getCodigo(), cod_people_list);
			resultFilmsPlanets = JdbcUtils.insertManyToManyTableStatement("films_planets", newFilm.getCodigo(), cod_planet_list);
			
			System.out.println("Insertando en la BD...");
			System.out.println("Se ha a??adido "+resultFilm+" registros a Films");
			System.out.println("Se ha a??adido "+resultFilmsPeople+ " registros a Films_People");
			System.out.println("Se ha a??adido "+resultFilmsPlanets+" registros a Films_Planets");
		}
		
		//Se limpian las listas en caso de que el usuario a??ada nuevos datos en la misma ejecuci??n del programa.
		cod_people_list.clear();
		cod_planet_list.clear();
    }
    
    /**
     * Crea un nuevo Film utilizando PreparedStatement
     */
    private static void createNewFilmPreparedStatement() {
    	
    	Map<Long, List<Long>> films_peopleMap = new HashMap<Long, List<Long>>();
    	Map<Long, List<Long>> films_planetMap = new HashMap<Long, List<Long>>();
    	
    	Films newFilm = createNewFilmAux();
    	List<Films> filmsList = new ArrayList<Films>();
    	filmsList.add(newFilm);
    	
		showCodigoAndName(SQL_SELECT_PEOPLE);
		List<Long> cod_people_list = getCodigosFromTable("\nSeleccione el c??digo del personaje  que aparece en el nuevo film y puslse \"intro\""
				+ " para seguir a??adiendo m??s ( o pulse \"0\" para terminar): ");
		films_peopleMap.put(((long) newFilm.getCodigo()), cod_people_list);
		
		
		showCodigoAndName(SQL_SELECT_PLANETS);
		List<Long> cod_planet_list = getCodigosFromTable("\nSeleccione el c??digo del planeta que aparece en el nuevo film y puslse \"intro\""
				+ " para seguir a??adiendo m??s ( o pulse \"0\" para terminar): ");
		films_planetMap.put(((long)newFilm.getCodigo()), cod_planet_list);
		
		if(confirmation()) {
			int resultNewFilm = JdbcUtils.insertFilms(SQL_INSERT_FILMS, filmsList);
			int resultNewFilmsPeople = JdbcUtils.insertManyToManyTables(SQL_INSERT_FILMS_PEPOPLE, films_peopleMap);
			int resultNewFilmsPlanets = JdbcUtils.insertManyToManyTables(SQL_INSERT_FILMS_PLANETS, films_planetMap);
			System.out.println("Insertando en la BD...");
			System.out.println("Se ha a??adido "+resultNewFilm+" registros a Films");
			System.out.println("Se ha a??adido "+resultNewFilmsPeople+ " registros a Films_People");
			System.out.println("Se ha a??adido "+resultNewFilmsPlanets+" registros a Films_Planets");
		} 
		//Se limpian las listas en caso de que el usuario a??ada nuevos datos en la misma ejecuci??n del programa.
		filmsList.clear();
		cod_people_list.clear();
		cod_planet_list.clear();
		films_peopleMap.clear();
		films_planetMap.clear();
    }
    
   /**
    * Obtine los datos introducidos por el usuario para crear un nuevo Film
    * @return Objeto de tipo Film
    */
    private static Films createNewFilmAux(){
    	Films newFilm = new Films();
    	String title = validateString("T??tulo: ", 100);
		//String episode_id = validateString("ID: ", 100);
		String opening_crawl = validateString("Introducci??n: ", 10485760);
		String director = validateString("Director: ", 100);
		String producer = validateString("Productor: ", 100);
		LocalDate release_date = validateLocalDate("Fecha de estreno (dd-mm-yyyy): ");
		LocalDate created = validateLocalDate("Fecha de creaci??n (dd-mm-yyyy): ");
		LocalDate edited = validateLocalDate("Fecha de edici??n (dd-mm-yyyy): ");
    	
    	int codigo = JdbcUtils.getTheLastCodigoOfFilms();
		if(codigo > -1) {
			newFilm = new Films((codigo +1), title, String.valueOf(codigo+1),opening_crawl, director, 
									producer, release_date.toString(), created.toString(), edited.toString());
		}
		
		return newFilm;
    }
    
    /**
     * Obtiene los c??digos de People y Planets seleccionados por el usuario.
     * @param msg String correspondiente al mensaje a mostrar.
     * @return Lista con los c??digos seleccionados
     */
    private static List<Long> getCodigosFromTable(String msg){
    	List<Long> cod_list = new ArrayList<Long>();//--> To store the people index select by de user
    	long codigo;
    	System.out.println(msg);
    	codigo = 0;
		do {
			codigo = sc.nextLong();
			if(codigo != 0) 
				cod_list.add(codigo);
			
		}while(codigo != 0);
		sc.nextLine();
		
		return cod_list;
    }
    
    /**
     * Muestra el c??digo y el nombre de la tabla pasada como par??metro
     */
    private static void showCodigoAndName(String sql) {
    	ResultSet rsPeople= JdbcUtils.getTable(sql);
    	try {
			if(rsPeople.isBeforeFirst()) {
				while(rsPeople.next()) 
					System.out.println("C??digo: "+rsPeople.getInt(1) +" Nombre: "+rsPeople.getString(2));
			}
			rsPeople.close();
    		
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error mostrando los datos de la tabla.");
			e.printStackTrace();
		}
    }
    
    /**
     * Muestra en pantalla la b??squeda de pel??culas por t??tulo
     */
    private static void showFilmsByTitle() {
    	
    	String titleToSearch = validateString("T??tulo de la pel??cula: ", 100);
		ResultSet rs = JdbcUtils.searchFilmByTitle(titleToSearch);
    	
    	try {
			if(rs.isBeforeFirst()) {
				while(rs.next()) {
					System.out.println("T??tulo: "+rs.getString(1)+
							";  Director: "+rs.getString(2)+
							";  Personaje: "+rs.getString(3));
				}
				rs.close();
				
			}else {
				System.out.println("No se han encontrado resultados.");
				rs.close();
			}
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error buscando la pel??cula.");
			e.printStackTrace();
		}
    }
    
    /**
     * Muestra todos los personajes sin starships
     */
    private static void showPeopleWithoutStarships() {
	   ResultSet rs = JdbcUtils.getPeopleWithoutStarships();
	   try {
		   if(rs.isBeforeFirst()) {
			   System.out.println("\nPersonas sin Starship: ");
				while(rs.next()) {
					System.out.println(rs.getString(1));
				}
		   }else
			   System.out.println("No se han encontrado resultados.");		
			
	   } catch (SQLException e) {System.out.println("Ha ocurrido un error al obtener los personajes.");}
   }
    
    
    
    //----------------------------------- FUNCIONES ADICIONALES -------------------------------------------//
    
    /**
     * Pregunta al usuario si desea guardar la informaci??n en la BD.
     * @return Boolean, true si confirma o false si cancela.
     */
    private static boolean confirmation() {
    	String confirmation;
    	boolean exit = false;
		boolean result = false;
		
		do {
			System.out.println("??Seguro que desea a??adir la informaci??n? (s/n)");
			confirmation = sc.nextLine();
			if(confirmation.equals("s")) {
				System.out.println("A??adido a la BD");
				result = true;
				exit = true;
			}else if(confirmation.equals("n")) {
				System.out.println("No se han guardado los datos.");
				result = false;
				exit = true;
			}else {
				System.out.println("Debe introducir:  \"s\" para confirmar o \"n\" para cancelar");
				result = false;
				exit = false;
			}
			
		}while(!exit);
		
		return result;
    }
    
    /**
     * Valida una cadena de texto introducida por el usuario
     * @param msg String correspondiente al mensaje a mostrar.
     * @param stringLength Integer correspondiente al tama??o m??ximo permitido de la cadena.
     * @return String validado.
     */
   	private static String validateString(String msg, int stringLength) {
  		String result;
  		System.out.println(msg);
  		do {
  			result = sc.nextLine();
  			if(result.isEmpty() || result.isBlank()) {
  				System.out.println("El campo no puede quedar vac??o");
  				System.out.println(msg);
  			}
  			
  			if(result.length() > stringLength) {
  				System.out.println("El dato a introducir no puede contener m??s de: "+stringLength+" caracteres.");
  				System.out.println(msg);
  			}
  		} while (result.isEmpty() || result.isBlank() || result.length() > stringLength);
  		
  		return result;
  	}
   	
   	/**
   	 * Valida una fecha con formato dd-MM-yyyy introducida por el usuario.
   	 * @param msg String correspondiente al mensaje a mostrar
   	 * @return LocalDate con formato dd-MM-yyyy.
   	 */
  	private static LocalDate validateLocalDate(String msg){
      	
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
  				System.out.println("Debe introducir un formato correcto: (dd-mm-yyyy).");
  				System.out.println(msg);
  				dateOK = false;
  			}
      	}while(!dateOK);
      	
      	return date;
      }
}
