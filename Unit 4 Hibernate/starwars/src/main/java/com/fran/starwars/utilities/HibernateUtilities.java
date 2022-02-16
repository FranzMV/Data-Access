package com.fran.starwars.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;

import com.fran.starwars.model.Films;
import com.fran.starwars.model.People;
import com.fran.starwars.model.Planets;

/**
 * @author Francisco David Manzanedo
 */
public class HibernateUtilities {
	
	static SessionFactory sessionFactory = null;
	static Session session = null;
	static Transaction trans = null;

	public static Session conectar() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
		session = sessionFactory.openSession();
		return session;
	}

	public static void desconectar() {
		if (session != null) 
			session.close();
		
		if (sessionFactory != null) 
			sessionFactory.close();
	}

	/**
	 * Descativa el log en consola
	 */
    public static void desactivarLog() {
    	@SuppressWarnings("unused")
    	org.jboss.logging.Logger logger =
    	org.jboss.logging.Logger.getLogger("org.hibernate");
    	java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
    }
    
	

	/**
	 * Dada una clase devuelve una lista con todos los elementos que tiene
	 * @param <T>   Tipo de la clase
	 * @param clase Clase pasada
	 * @return
	 */
	public static <T> List<T> devolverClase(Class<T> clase) {
		return session.createQuery("From " + clase.getSimpleName(), clase).list();
	}

	/**
	 * Obtiene un elemento recibido como parámetro a través del campo codigo en la base de datos.
	 * @param <T> Clase con la que trabajamos.
	 * @param codigo String código de la clase que queremos obtener.
	 * @return List<T> de objetos que cumplen la condición.
	 */
	public static <T>List<T> getElementById(Class<T> clase, String codigo){
		return session.createQuery("FROM " +clase.getSimpleName()+" WHERE codigo="+codigo, clase).list();
	
		
	}
	/**
	 * Inserta el registro en su tabla correspondiente de la base de datos.
	 * @param registro Objeto de la clase que queremos insertar
	 * @return True si la inserción es correcta. False si falla.
	 */
	public static boolean save(Object object) {
		try {
			trans = session.beginTransaction();
			session.save(object);
			trans.commit();
			return true;
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error insertando el "+object.getClass().getName());
			e.printStackTrace();
			trans.rollback();
			return false;
		}
	}

	/**
	 * Borramos los objetos de la clase que cumplen la condición Where pasada.
	 * @param <T> Clase con la que trabajamos 
	 * @param clase Objeto de la clase.
	 * @param where Condición de borrado.
	 * @return True si borra todos los registros. False en caso contrario.
	 */
	public static <T> boolean delete(Class<T> clase, String where) {
		try {
			@SuppressWarnings("unchecked")
			List<T> resultados = session.createQuery("FROM " + clase.getSimpleName() + " " + where).list();			
			trans = session.beginTransaction();
			resultados.forEach(r->session.delete(r));
			trans.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			return false;
		}
	}

	/**
	 * Actualiza los datos de un objeto pasado como parámetro y tiene su reflejo en la base de datos.
	 * @param object Objeto a actualizar su información.
	 * @return boolean a true si se ha podido actualizar, false en caso contrario.
	 */
	public static boolean updateElement(Object object) {
		boolean resultUpdate = false;
		try {
			trans = session.beginTransaction();
			session.update(object);
			trans.commit();
			resultUpdate = true;
		}catch (Exception e) {
			trans.rollback();
			System.out.println("No se ha podido actualizar el registro");
			resultUpdate = false;
		}
		return resultUpdate;
	}
	

	/**
	 * Obtiene una tabla filtrando por nombre.
	 * @param <T> Typo
	 * @param name String nombre a buscar en la tabla.
	 * @param class1 Tipo de clase en la que buscar.
	 * @return Lista genérica con el tipo de clase pasada como parámetro. 
	 */
	public static <T> List<T> findElementByName(String name, Class<T> object){
		Query<T> query = session.createQuery("FROM " +object.getSimpleName()+" p WHERE LOWER(p.name)"
				+ " LIKE LOWER(:name)", object);
		query.setParameter("name", '%'+name+'%', StringType.INSTANCE);
		return query.list();
	}
	
	/**
	 * Obtiene los personajes sin especie mediante una consulta en HQL
	 * @return List<People> con los personajes que cumplen la condición.
	 */
	public static List<People> getPeopleWithoutSpecie(){
		final String queryString ="FROM People p WHERE p.codigo NOT IN ( "
				+ "SELECT s.codigo FROM Species s) ";
		return session.createQuery(queryString, People.class).list();
	}
	
	/**
	 * Obtiene a los personajes que ha conducido más naves mediante una consulta HQL.
	 * @return  List<People> con los personajes que cumplen la condición.
	 */
	public static List<People> getPeopleHaveDrivenMostStarships(){
		final String queryString ="FROM People as p WHERE p.codigo IN ( "
				+ "SELECT s FROM Starships as s "
				+ "GROUP BY s.codigo "
				+ "ORDER BY COUNT(s.codigo) DESC)";
		Query<People> query = session.createQuery(queryString, People.class);
		return query.list();
	}
	
	/**
	 * Obtiene el color de ojos más repetido en personajes
	 * @return Map<String,Long> Mapa clave valor correspondiente al color de ojos y el número de veces que se repite.
	 */
	public static Map<String, Long> getMostCommonPeopleEyeColor(){
		final String queryString ="SELECT people.eyeColor, COUNT(people.eyeColor) as total "
				+ "FROM People people "
				+ "GROUP BY people.eyeColor "
				+ "ORDER BY total DESC";
		Query<?> query = session.createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Object[]> result = (List<Object[]>) query.list();
		Map<String, Long> mapResult = new HashMap<String, Long>();
		for(Object[] e: result)
	        mapResult.put((String)e[0], (Long)e[1]);
	         
		return mapResult;
	}
	
	/**
	 * Obtiene los planetas filtrando por nombre utilizando una NamedQuery
	 * @param name String nombre del planeta a buscar.
	 * @return List<Planets> Lista de planetas que cumplen la condición.
	 */
	public static List<Planets> findPlanetsByName(String name){
		return session.createNamedQuery("Planets.findByName", Planets.class)
					  .setParameter("name", name)
					  .getResultList();
	}
	
	/**
	 * Obtiene los planetas que no tienen una specie asociada a ellos utilizando una NamedQuery.
	 * @return List<Planets> Lista de planetas que cumplen la condición.
	 */
	public static List<Planets> getPlanetsWithoutSpecies(){
		return session .createNamedQuery("Planets.withoutSpecies", Planets.class)
					   .getResultList();	
	}
	
	/**
	 * Obtiene los films filtrando por nombre utilizando una NativeQuery.
	 * @param title String título de la película a buscar.
	 * @return List<Films> Lista de films que cumplen la condición.
	 */
	@SuppressWarnings("unchecked")
	public static List<Films> findFilmsByTitle(String title){
		return session.createNativeQuery("SELECT * FROM Films WHERE LOWER(title) like LOWER(:title)")
					  .addEntity(Films.class)
					  .setParameter("title",'%'+title+'%')
					  .list();
	}
	
	/**
	 * Obtiene el útimo código de la tabla que se le pasa como parámetro.
	 * @param table String correspondiente al nombre de la tabla.
	 * @return integer último código + 1 para poder insertar un nuevo registro en la tabla correspondiente.
	 */
	public static int getLastCodigo(String table) {
		Integer count = (Integer) session.createSQLQuery("SELECT MAX(codigo) FROM "+table).uniqueResult();
		return count+1;
	}
}
