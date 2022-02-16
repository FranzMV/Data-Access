package com.fran.practicatema1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fran.practicatema1.entidades.Covid19Deceased;
import com.fran.practicatema1.entidades.CumulativeIncidence;
import com.fran.practicatema1.entidades.NewPcrPositiveByGenre;
import com.fran.practicatema1.entidades.TotalPcrsByDate;
import com.fran.practicatema1.fileutils.FileUtils;
import com.fran.practicatema1.jsonutils.JsonUtils;
import com.fran.practicatema1.serializeutils.SerializeUtils;
import com.fran.practicatema1.xmlutils.XMLUtils;

public class App 
{
	private static Scanner sc = new Scanner(System.in);
	
    public static void main( String[] args )
    {
    	final String URL_EPIDEMIC_STATUS = "https://opendata.euskadi.eus/contenidos/ds_informes_estudios/covid_19_2020/opendata/generated/covid19-epidemic-status.json";
    	final String URL_DECEASED_BY_DATE ="https://opendata.euskadi.eus/contenidos/ds_informes_estudios/covid_19_2020/opendata/generated/covid19-deceasedPeopleCount.xml";
    	final String URL_PCR_POSITIVE_BY_GENRE="https://opendata.euskadi.eus/contenidos/ds_informes_estudios/covid_19_2020/opendata/generated/covid19-pcr-positives.json";
    	final String URL_EPIDEMIC_STATUS_OTHER_SOURCE = "https://api.covid19tracking.narrativa.com/api/country/spain?date_from=2020-03-20&date_to=2020-03-22";
        final String FOLDER_NAME = "situacion_epidemiologica";
    	final String CSV_FILENAME = "02.csv";
    	final String CUMULATIVE_INCIDENCE_SEARCH_SERIALIZE = "cumulative_incidence.dat";
    	//Cargar los datos del fichero CSV en la lista
    	List<CumulativeIncidence> cumulativeIncidencesList = FileUtils.readFile(FOLDER_NAME, CSV_FILENAME);
    	//Desserializar
	    List<CumulativeIncidence> searchByDateList =  SerializeUtils.deserializeCumulativeIncidence(CUMULATIVE_INCIDENCE_SEARCH_SERIALIZE);
	    
        String opcionMenu;
       
        do {
        	System.out.println("\n1.- Calcular total de Pcr's dentro un rango de fechas.");
        	System.out.println("2.- Listado de 3 dás con el máximo Nº de fallecidos.");
        	System.out.println("3.- Indicencia acumulado 14 días X 100.000 habit. en Euskadi.");
        	System.out.println("4.- Historial consultas de incidencia acumulada X 100.000 habitantes.");
        	System.out.println("5.- Balance de Pcr's positivos sengun género.");
        	System.out.println("6.- Comparar fuentes.");
        	System.out.println("0.- Salir.");
        	System.out.print("\nEscoja una opción: ");
        	opcionMenu = sc.nextLine();
        	
        	switch (opcionMenu) {
        	case "0": break;//Salir
        	
			case "1"://Calcular total de Pcr's dentro un rango de fechas
				List<TotalPcrsByDate> pcrResults = JsonUtils.getPcrsPerformedByDate(URL_EPIDEMIC_STATUS);
				//pcrResults.forEach(System.out::println); 
				LocalDate date1 = validateLocalDate("Introduce la primera fecha (yyyy-mm-dd): ");
				LocalDate date2 = validateLocalDate("Introduce la segunda fecha (yyyy-mm-dd): ");
				
				Optional<TotalPcrsByDate> date1Found = pcrResults.stream()
			        		.filter(p-> p.getDate().equals(date1))
			        		.findFirst();
				
				Optional<TotalPcrsByDate> date2Found = pcrResults.stream()
			        		.filter(p-> p.getDate().equals(date2))
			        		.findFirst();
			     
				if(date1Found.isPresent() && date2Found.isPresent()) {
			        List<LocalDate> datesList1 = date1.datesUntil(date2.plusDays(1))
			        		.collect(Collectors.toList());
			       
			        long count = pcrResults.stream()
			        		.filter(p-> datesList1.contains(p.getDate()))
			        		.mapToLong(p-> p.getNumberOfPcrsPerformed())
			        		.sum();
					
					String result = count > 0 ? String.valueOf(count) 
							+" Pcr's realizados entre las fechas: "+date1+" y "+date2
						  	: "No se han encontrado resultados.";
					System.out.println(result);
					
				}else
					System.out.println("No se han encontrado resultados para el rango de fechas: "+date1+" y "+date2);
				break;
			
			case "2"://Listado de 3 dás con el máximo Nº de fallecidos.
				List<Covid19Deceased> deceasedByDateList = XMLUtils.getCovidDeceasedByDate(URL_DECEASED_BY_DATE);
				//deceasedByDateList.forEach(System.out::println);
				LocalDate date3 = validateLocalDate("Introduce la primera fecha (yyyy-mm-dd): ");
				LocalDate date4 = validateLocalDate("Introduce la segunda fecha (yyyy-mm-dd): ");
				
				Optional<Covid19Deceased> date3Found = deceasedByDateList.stream()
		        		.filter(p-> p.getDate().equals(date3))
		        		.findFirst();
			
				Optional<Covid19Deceased> date4Found = deceasedByDateList.stream()
		        		.filter(p-> p.getDate().equals(date4))
		        		.findFirst();
				
				if(date3Found.isPresent() && date4Found.isPresent()) {
					
					List<LocalDate> datesList2 = date3.datesUntil(date4.plusDays(1))
			        		.collect(Collectors.toList());
					
					List<Covid19Deceased> result = deceasedByDateList.stream()
							.filter(p-> datesList2.contains(p.getDate()))
							.sorted(Comparator.comparing(Covid19Deceased::getCount).reversed())
							.limit(3)
							.collect(Collectors.toList());
					
					System.out.println("Resultados entre las fechas: "+date3.toString()+" y "+date4.toString());
					result.forEach(System.out::println);
					
				}else 
					System.out.println("No se han encontrado resultados para el rango de fechas: "+date3+" "+date4);
				break;

			case "3": //Indicencia acumulado 14 días X 100.000 habit. en Euskadi
				System.out.println("Incidencia acumulada 14 días x 100.000 habitantes.");
				//cumulativeIncidencesList.forEach(e-> System.out.println(e));
				LocalDate date5 = validateLocalDate("Introduce fecha (yyyy-mm-dd): ");
				
				//Comprueba si en la lista hay consultas repetidas por fecha.
				Optional<CumulativeIncidence> dataExist = searchByDateList.stream()
						.filter(p-> p.getDate().equals(date5))
						.findFirst();
				//Si no se ha guardado el registro de la consulta anteriormente, lo guardamos
				if (dataExist.isEmpty()) {
					for (CumulativeIncidence c : cumulativeIncidencesList) {
						if (date5.isEqual(c.getDate())) 
							searchByDateList.add(c);
					}
					System.out.println("Resultado para la fecha "+date5.toString()+": ");
					searchByDateList.stream()
							.filter(p -> p.getDate().isEqual(date5))
							.forEach(System.out::println);
					//Serializa
					SerializeUtils.serializeCumulativeIncidence(searchByDateList, CUMULATIVE_INCIDENCE_SEARCH_SERIALIZE);
					//Desserializa
					searchByDateList = SerializeUtils.deserializeCumulativeIncidence(CUMULATIVE_INCIDENCE_SEARCH_SERIALIZE);

				} else
					System.out.println("La consulta realizada no se guardará en el histórico porque ya existe.");
				break;
		
			case "4"://Historial consultas de incidencia acumulada X 100.000 habitantes
				if(searchByDateList.size() >0) {
					System.out.println("Historial consultas incidencia acumulada por cada 100.000 habitantes: ");
					searchByDateList = SerializeUtils.deserializeCumulativeIncidence(CUMULATIVE_INCIDENCE_SEARCH_SERIALIZE);
					searchByDateList.forEach(e -> System.out.println(e));
				}else 
					System.out.println("No existe consultas almacenadas.");
				break;

			case "5"://Balance de Pcr´s positivos sengun género
				List<NewPcrPositiveByGenre> newPcrGenresList = JsonUtils.getNewPcrPositiveByGenres(URL_PCR_POSITIVE_BY_GENRE);
				System.out.println("Balance de Pcr's positivos sengún género:");
				
				long womenCount = newPcrGenresList.stream()
						.filter(p-> p.getWomenCount() > p.getMenCount())
						.count();
				
				long menCount = newPcrGenresList.stream()
						.filter(p-> p.getMenCount() > p.getWomenCount())
						.count();
				
				System.out.println("Días que ha habido más Pcr's positivos de mujeres: "+womenCount);
				System.out.println("Días que ha habido más Pcr's positivos de hombres: "+menCount);	
				break;

			case "6"://Comparar fuentes
				System.out.println("Comparar fuentes.");
				JsonUtils.otherSourceAPI(URL_EPIDEMIC_STATUS_OTHER_SOURCE);
				break;
			
			default://Opciones no permitidas
				System.out.println("Opción no permitida");
				break;
			}
        	
		} while (!opcionMenu.equals("0"));
        
        System.out.println("\nHasta pronto!");
    }
    
    //Valida una fecha de tipo LocalDate con formato yyyy-MM-dd
    private static LocalDate validateLocalDate(String msg){
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	LocalDate date = LocalDate.now();
    	boolean dateOK = false;
    	String dateString= "";
    	
    	do {
    		System.out.println(msg);
    		dateString = sc.nextLine();
	    	try {
				date = LocalDate.parse(dateString, formatter);
				dateOK = true;
			} catch (DateTimeParseException e) {
				System.out.println("Debe introducir un formato correcto: (yyyy-MM-dd).");
				dateOK = false;
			}
    	}while(!dateOK);
    	return date;
    }
}
