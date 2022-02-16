package com.fran.swapi.jdbcutils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.fran.swapi.entidades.Films;
import com.fran.swapi.entidades.People;
import com.fran.swapi.entidades.Planets;
import com.fran.swapi.entidades.Species;
import com.fran.swapi.entidades.Starships;
import com.fran.swapi.entidades.Vehicles;

/**  @author Francisco David Manzanedo */
public class JdbcUtils {
	
	private static Connection con = null;
	private static Statement st = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;
	
	
	/**
	 * Conecta con la Base de datos
	 * @param url String correspondiente a la dirección de la base de datos
	 * @param user String correspondiente al nombre de usuario de la base de datos
	 * @param password String correspondiente a el password de la base de datos
	 */
	public static void connectDB(String url, String user, String password) {
		
		try {
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Conexión establecida");
		}catch (SQLException  e) {
			System.out.println("Ha ocurrido un problema al conectar la base de datos");
			e.printStackTrace();
		}
	}
	
	/**Desconecta la Base de datos*/
	public static void disconnetDB() {
		try {
			con.close();
			System.out.println("Conexión cerrada");
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un problema al desconectar la base de datos.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserta los registros obtenidos de los Json en la tabla Planets
	 * @param sql String correspondiente al Insert del registro
	 * @param planets ArrayList con los datos de tipo Planets
	 * @return Integer El número de los registros insertados en la tabla o -1 si no se ha podido insertar
	 */
	public static int insertPlanets(String sql, List<Planets> planetsList) {
		
		try {
			ps= con.prepareStatement(sql);
			int count = 0;
			for (Planets p : planetsList) {
				ps.setInt(1, p.getCodigo());
				ps.setString(2, p.getName());
				ps.setString(3, p.getDiameter());
				ps.setString(4, p.getRotation_period());
				ps.setString(5, p.getOrbital_period());
				ps.setString(6, p.getGravity());
				ps.setString(7, p.getPopulation());
				ps.setString(8, p.getClimate());
				ps.setString(9, p.getTerrain());
				ps.setString(10, p.getSurface_water());
				ps.setString(11, p.getCreated());
				ps.setString(12, p.getEdited());
			    ps.executeUpdate();
			    count++;
			}
			return count;
		}catch (SQLException e) { System.out.println("Ha ocurrido un error insertando Planets"); }
		
		return -1;
	}
	
	/**
	 * Inserta los registros obtenidos de los Json en la tabla People
	 * @param sql String correspondiente al Insert del registro
	 * @param people ArrayList con los datos de tipo People
	 * @return Integer El número de los registros insertados en la tabla o -1 si no se ha podido insertar.
	 */
	public static int insertPeople(String sql, List<People> people) {
		try {
			ps= con.prepareStatement(sql);
			int count = 0;
			for (People p : people) {
				ps.setInt(1, p.getCodigo());
				ps.setString(2, p.getName());
				ps.setString(3, p.getBirth_year());
				ps.setString(4, p.getEye_color());
				ps.setString(5, p.getGender());
				ps.setString(6, p.getHair_color());
				ps.setString(7, p.getHeight());
				ps.setString(8, p.getMass());
				ps.setString(9, p.getSkin_color());
				ps.setInt(10, p.getHomeworld());
				ps.setString(11, p.getCreated());
				ps.setString(12, p.getEdited());
			    ps.executeUpdate();
			    count++;
			}
			return count;
			
		}catch (SQLException e) { System.out.println("Ha ocurrido un error al insertar People"); }
		
		return -1;
	}
	
	/**
	 * Inserta los registros obtenidos de los Json en la tabla Starships
	 * @param sql String correspondiente al Insert del registro
	 * @param starships ArrayList con los datos de tipo Starships
	 * @return Integer El número de los registros insertados en la tabla o -1 si no se ha podido insertar.
	 */
	public static int insertStarships(String sql, List<Starships> starships) {
		
		try {
			ps= con.prepareStatement(sql);
			int count = 0;
			for (Starships s : starships) {
				ps.setInt(1, s.getCodigo());
				ps.setString(2, s.getName());
				ps.setString(3, s.getModel());
				ps.setString(4, s.getStarship_class());
				ps.setString(5, s.getManufacturer());
				ps.setString(6, s.getCost_in_credits());
				ps.setString(7, s.getLength());
				ps.setString(8, s.getCrew());
				ps.setString(9, s.getPassengers());
				ps.setString(10, s.getMax_atmosphering_speed());
				ps.setString(11, s.getHyperdrive_rating());
				ps.setString(12, s.getMGLT());
				ps.setString(13, s.getCargo_capacity());
				ps.setString(14, s.getConsumables());
				ps.setString(15, s.getCreated());
				ps.setString(16, s.getEdited());
			    ps.executeUpdate();
			    count++;
			}
			return count;
		}catch (SQLException e) { System.out.println("Ha ocurrido un error al insertar Starships"); }
		
		return -1;
	}
	
	/**
	 * Inserta los registros obtenidos de los Json en la tabla Species
	 * @param sql String correspondiente al Insert del registro
	 * @param species ArrayList con los datos de tipo Species
	 * @return Integer El número de los registros insertados en la tabla o -1 si no se ha podido insertar.
	 */
	public static int insertSpecies(String sql, List<Species> species) {
		
		try {
			ps = con.prepareStatement(sql);
			int count = 0;
			for (Species s : species) {
				if(s.getHomeworld() ==  null) {
					ps.setInt(1, s.getCodigo());
					ps.setString(2, s.getName());
					ps.setString(3, s.getClassification());
					ps.setString(4, s.getDesignation());
					ps.setString(5, s.getAverage_height());
					ps.setString(6, s.getAverage_lifespan());
					ps.setString(7, s.getEye_colors());
					ps.setString(8, s.getHair_colors());
					ps.setString(9, s.getSkin_colors());
					ps.setString(10, s.getLanguage());
					ps.setNull(11, java.sql.Types.INTEGER);
					ps.setString(12, s.getCreated());
					ps.setString(13, s.getEdited());
				    ps.executeUpdate();
				}else {
					ps.setInt(1, s.getCodigo());
					ps.setString(2, s.getName());
					ps.setString(3, s.getClassification());
					ps.setString(4, s.getDesignation());
					ps.setString(5, s.getAverage_height());
					ps.setString(6, s.getAverage_lifespan());
					ps.setString(7, s.getEye_colors());
					ps.setString(8, s.getHair_colors());
					ps.setString(9, s.getSkin_colors());
					ps.setString(10, s.getLanguage());
					ps.setInt(11, s.getHomeworld());
					ps.setString(12, s.getCreated());
					ps.setString(13, s.getEdited());
				    ps.executeUpdate();
				}
			    count++;
			}	
			return count;
		}catch (SQLException e) { System.out.println("Ha ocurrido un error al insertar Species"); }
		return -1;
	}
	
	/**
	 * Inserta los registros obtenidos de los Json en la tabla Films
	 * @param sql String correspondiente al Insert del registro
	 * @param films ArrayList con los datos de tipo Films
	 * @return Integer El número de los registros insertados en la tabla o -1 si no se ha podido insertar.
	 */
	public static int insertFilms(String sql, List<Films> films) {
		
		try {
			ps= con.prepareStatement(sql);
			int count = 0;
			for (Films f : films) {
				ps.setInt(1, f.getCodigo());
				ps.setString(2, f.getTitle());
				ps.setString(3, f.getEpisode_id());
				ps.setString(4, f.getOpening_crawl());
				ps.setString(5, f.getDirector());
				ps.setString(6, f.getProducer());
				ps.setString(7, f.getRelease_date());
				ps.setString(8, f.getCreated());
				ps.setString(9, f.getEdited());
			    ps.executeUpdate();
			    count++;
			}	
			return count;
		}catch (SQLException e) { System.out.println("Ha ocurrido un error al insertar Films"); }
		
		return -1;
	}
	
	/**
	 * Inserta los registros obtenidos de los Json en la tabla Vehicles
	 * @param sql String correspondiente al Insert del registro
	 * @param vehicles ArrayList con los datos de tipo Vehicles
	 * @return Integer El número de los registros insertados en la tabla o -1 si no se ha podido insertar.
	 */
	public static int insertVehicles(String sql, List<Vehicles> vehicles) {
		
		try {
			ps= con.prepareStatement(sql);
			int count = 0;
			for (Vehicles v : vehicles) {
				ps.setInt(1, v.getCodigo());
				ps.setString(2, v.getName());
				ps.setString(3, v.getModel());
				ps.setString(4, v.getVehicle_class());
				ps.setString(5, v.getManufacturer());
				ps.setString(6, v.getLength());
				ps.setString(7, v.getCost_in_credits());
				ps.setString(8, v.getCrew());
				ps.setString(9, v.getPassengers());
				ps.setString(10, v.getMax_atmosphering_speed());
				ps.setString(11, v.getCargo_capacity());
				ps.setString(12, v.getConsumables());
				ps.setString(13, v.getCreated());
				ps.setString(14, v.getEdited());
			    ps.executeUpdate();
			    count++;
			}	
			return count;
		}catch (SQLException e) { System.out.println("Ha ocurrido un error al insertar Vehicles"); }
		
		return -1;
	}
	
	/**
	 * Inserta los registros en las tablas cuya relación es M:M
	 * @param sql String correspondiente al Insert del registro
	 * @param map HashMap con los id's a cuyas tablas hacen referencia.
	 * @return Integer El número de los registros insertados en la tabla o -1 si no se ha podido insertar.
	 */
	public static int insertManyToManyTables(String sql, Map<Long, List<Long>> map) {
		
		int count = 0;
		try {
			
			ps = con.prepareStatement(sql);
			for(Map.Entry<Long, List<Long>> entry : map.entrySet()){
				long key = entry.getKey();
				List<Long> valueList = entry.getValue();
				ps.setLong(1, key);
				for(Long value : valueList) {
					ps.setLong(2, value);
					ps.executeLargeUpdate();
					count++;
				}
			}
			//map.entrySet().stream().forEach(e-> System.out.println(e.getKey()+": "+e.getValue()));
		}catch (SQLException e) { System.out.println("Ha ocurrido un error insertado en las tablas M:M");  }
		
		return count;
	}
	
	/**
	 * Inserta un nuevo film en la base de datos cuyos datos los ha introducido el usuario
	 * @param film Objeto de tipo film
	 * @return int correspondiente al número de registros afectados.
	 */
	public static int insertNewFilmStatement(Films film) {
		
		final String SQL= "INSERT INTO films "
				+ "(codigo, title, episode_id, opening_crawl, director, "
				+ "producer, release_date, created, edited) "
				+ "VALUES("+film.getCodigo()+", '"+film.getTitle()+"','"+film.getEpisode_id()+"','"+film.getOpening_crawl()+
				"','"+film.getDirector()+"', '"+film.getProducer()+"', '"+film.getRelease_date()+"','"+film.getCreated()+"', '"+
				film.getEdited()+"');";
				
		try {
			st = con.createStatement();
			return st.executeUpdate(SQL);
		} catch (SQLException e) {
			System.out.println("Error añadiendo Film con statement.");
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Inserta los códigos de films, people y planets en las tablas 
	 * films_people y planets_people (seleccionados por el usuario)
	 * @param tableName String nombre de la tabla.
	 * @param codigoFilm Codigo del nuevo film añadido por el usuario.
	 * @param values códigos correspondientes a people y planets, seleccionados por el usuario.
	 * @return int correspondiente al número de registros afectados.
	 */
	public static int insertManyToManyTableStatement(String tableName, int codigoFilm, List<Long> values) {
		
		int result = 0;
		try {
			st = con.createStatement();
			for (Long value : values) 
				  result += st.executeUpdate("INSERT INTO "+tableName+" VALUES ("+codigoFilm+","+value.longValue()+");");
			
			return result;
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error insertando en la tabla "+tableName);
			e.printStackTrace();
			return -1;
		}
		
	}
	
	/**
	 * Elimina todas las tablas de la base de datos
	 * @param tableNames ArrayList con el nombre de todas las tablas
	 * @return Integer correspondiente al número de tablas afectadas.
	 */
	public static int dropTables(List<Object> tableNames) {
		
		int count = 0;
		try {
			st = con.createStatement();
			for(int i = 0; i<tableNames.size(); i++) {
				st.execute("DROP TABLE IF EXISTS "+tableNames.get(i)+";");
				count++;	
			}
			st.close();
		}catch (SQLException e) { 
			System.out.println("Ha ocurrido un error borrando las tablas");
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 * Crea todas las tablas de la base de datos
	 * @param sqlCreateTables ArrayList con los CREATE TABLES correspondientes a cada tabla
	 * @return Integer correspondiente al número de tablas afectadas.
	 */
	public static int createTables(List<Object> sqlCreateTables) {
		
		int count = 0;
		try {
			
			st = con.createStatement();
			for(int i = 0; i<sqlCreateTables.size(); i++) {
				st.execute(sqlCreateTables.get(i).toString());
				count++;
			}
			st.close();
			return count;
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un creando las tablas");
			e.printStackTrace();
			return -1;
		}
		
	}
	
	/**
	 * Obtiene el nombre de personas que no tienen starships
	 * @return ResulSet con el resultado obteneido por el procedimiento almacenado.
	 */
	public static ResultSet getPeopleWithoutStarships() {
		
		final String SQL = "{call peoplewithoutstarships()}";
		try {
			cs = con.prepareCall(SQL);
			return rs = cs.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error al obtener el nombre de los personajes sin starships.");
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Realiza una búsqueda de films por titulo mediante un procedimiento almacenado.
	 * @param filmTitle Título de la película
	 * @return ResulSet con el resultado obteneido por el procedimiento almacenado.
	 */
	public static ResultSet searchFilmByTitle(String title) {
		
		final String SQL ="{call searchfilmbytitle('"+'%'+title+'%'+"')}";
		try {
			cs = con.prepareCall(SQL);
			return rs = cs.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error en la búsqueda de película.");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Obtiene el valor máximo en el campo código de Films para poder añadir un nuevo 
	 * registro a la bd.
	 * @return
	 */
	public static int getTheLastCodigoOfFilms() {
		final String SQL = "{call  getlastcodigooffilms()}";
		int result = 0;
		try {
			
			cs = con.prepareCall(SQL);
			rs = cs.executeQuery();
			while(rs.next()) 
				result = rs.getInt(1);
			
			return result;
		}catch (SQLException e) {
			System.out.println("Ha ocurrido un error obteniendo el valor máximo del campo código en Films");
			e.printStackTrace();
			return -1;
		}finally {
			try {
				if(rs != null) rs.close();
			} catch (SQLException e2) { }
		}
	}
	
	/**
	 * Obtiene los datos de una tabla pasados como parámentros.
	 * @return Resulset con los datos obtnidos.
	 */
	public static ResultSet getTable(String sql) {
		
		try {
			cs = con.prepareCall(sql);
			return rs = cs.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error al obtener los datos de los personajes.");
			e.printStackTrace();
			return null;
		}
	}	
}


