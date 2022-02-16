package com.fran.practicatema1.fileutils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fran.practicatema1.entidades.CumulativeIncidence;



public class FileUtils {
	
	private static final String SEPARATOR = ";";
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	

	/**
	 * 
	 * @param folderName
	 * @param fileName
	 * @return
	 */
	public static List<CumulativeIncidence> readFile(String folderName, String fileName){
		
		final int N = 2;
		
		try {
			return Files.lines(Paths.get(folderName+"/"+fileName),Charset.forName("ISO-8859-1"))
					.skip(N)
					.map(line -> new CumulativeIncidence(
							LocalDate.parse(line.split(SEPARATOR)[0], formatter),
							Double.parseDouble(line.split(SEPARATOR)[1].replace(',', '.')), 
							Double.parseDouble(line.split(SEPARATOR)[2].replace(',', '.')), 
							Double.parseDouble(line.split(SEPARATOR)[3].replace(',', '.')), 
							Double.parseDouble(line.split(SEPARATOR)[4].replace(',', '.')),
							Double.parseDouble(line.split(SEPARATOR)[5].replace(',', '.'))))
					.collect(Collectors.toList());
			
		} catch (IOException e) {
			System.out.println("Error leyendo el fichero: ".concat(fileName));
			e.printStackTrace();
		}
		
		return new ArrayList<CumulativeIncidence>();
	}
}
