package com.fran.gestionstarwars.controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fran.gestionstarwars.entity.People;
import com.fran.gestionstarwars.functions.HelperFunctions;
import com.fran.gestionstarwars.model.services.IPeopleService;
import com.fran.gestionstarwars.model.services.PeopleServiceImpl;
import com.fran.gestionstarwars.models.dto.PeopleDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PeopleRestController {

	@Autowired
	private IPeopleService peopleService;
	
	@Autowired
	private PeopleServiceImpl peopleServiceImpl;
	
		
	@GetMapping("/people")
	public List<PeopleDTO> index(){
		List<People> people = peopleService.findAll();
		List<PeopleDTO> peopleDTO = new ArrayList<PeopleDTO>();
		people.forEach( p-> peopleDTO.add(getPeopleDTO(p)));
		
		return peopleDTO;
	}
	
	
	@GetMapping("/people/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id){
		People people = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			people = peopleService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(people == null) {
			response.put("mensaje", "El personaje Código: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<PeopleDTO>( getPeopleDTO(people), HttpStatus.OK);
	}
	
	
	
	@PostMapping("/people")
	public ResponseEntity<?> create(@Valid @RequestBody People people, BindingResult result){
		
		People newPeople = null;
		people.setCodigo(HelperFunctions.getLastPeopleId(peopleServiceImpl));
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			newPeople = peopleService.save(people);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El personaje ha sido creado con éxito!");
		response.put("people", newPeople);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	
	@PutMapping("/people/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody People people, BindingResult result, @PathVariable Integer id){
		
		People actualPeople = peopleService.findById(id);
		People updatedPeople = null;
		
		Map<String, Object> response= new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(actualPeople == null) {
			response.put("mensaje", "Error: no se pudo editar el personaje código: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			actualPeople.setName(people.getName());
			actualPeople.setBirthYear(people.getBirthYear());
			actualPeople.setPlanets(people.getPlanets());
			actualPeople.setCreated(people.getCreated());
			actualPeople.setEdited(HelperFunctions.getDate());
			actualPeople.setEyeColor(people.getEyeColor());
			actualPeople.setHeight(people.getHeight());
			actualPeople.setMass(people.getMass());
			actualPeople.setHairColor(people.getHairColor());
			actualPeople.setSkinColor(people.getSkinColor());
			actualPeople.setGender(people.getGender());
			actualPeople.setFilms(people.getFilms());
			actualPeople.setVehicles(people.getVehicles());
			actualPeople.setSpecies(people.getSpecies());
			actualPeople.setStarships(people.getStarships());
			
			updatedPeople = peopleService.save(actualPeople);
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al conectar con la base de datos");
			response.put("error", e.getMessage().concat(":")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El personaje se ha modificado correctamente");
		response.put("people", updatedPeople);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping("/people/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Map<String,Object> response = new HashMap<>();
		try {
			peopleService.delete(id);
		} catch (DataAccessException e) {  // Error al acceder a la base de datos
			response.put("mensaje", "Error al eliminar el id");
			response.put("error", e.getMessage().concat(":")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El personaje se ha borrado correctamente");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	
	
	private static PeopleDTO getPeopleDTO(People people) {
		PeopleDTO peopleDTO = new PeopleDTO();
		peopleDTO.setCodigo(people.getCodigo());
		peopleDTO.setPlanets(people.getPlanets().getCodigo());
		peopleDTO.setName(people.getName());
		peopleDTO.setBirthYear(people.getBirthYear());
		peopleDTO.setEyeColor(people.getEyeColor());
		peopleDTO.setSkinColor(people.getSkinColor());
		peopleDTO.setHairColor(people.getHairColor());
		peopleDTO.setHeight(people.getHeight());
		peopleDTO.setMass(people.getMass());
		peopleDTO.setGender(people.getGender());
		peopleDTO.setCreated(people.getCreated());
		peopleDTO.setEdited(people.getEdited());
		
		List<Integer> codigoStraships = people.getStarships()
											  .stream()
											  .map(p-> p.getCodigo())
											  .collect(Collectors.toList());
		peopleDTO.setStarshisps(new HashSet<>(codigoStraships));
		
		List<Integer> codigoVehicles = people.getVehicles()
											 .stream()
											 .map(p-> p.getCodigo())
											 .collect(Collectors.toList());
		peopleDTO.setVehicles(new HashSet<>(codigoVehicles));
				
		List<Integer> codigoSpecies = people.getSpecies()
											.stream()
											.map(p-> p.getCodigo())
											.collect(Collectors.toList());
		peopleDTO.setSpecies(new HashSet<>(codigoSpecies));
			
		List<Integer> codigoFilms = people.getFilms()
										  .stream()
	 				  					  .map(p-> p.getCodigo())
	 				  					  .collect(Collectors.toList());
		peopleDTO.setFilms(new HashSet<>(codigoFilms));
		
		return peopleDTO;
	}
}
