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

import com.fran.gestionstarwars.entity.Species;
import com.fran.gestionstarwars.functions.HelperFunctions;
import com.fran.gestionstarwars.model.services.ISpeciesService;
import com.fran.gestionstarwars.model.services.SpeciesServiceImpl;
import com.fran.gestionstarwars.models.dto.SpeciesDTO;

import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class SpeciesRestController {
	
	@Autowired 
	private ISpeciesService speciesService;
	
	@Autowired
	private SpeciesServiceImpl speciesServiceImpl;
	
	
	@GetMapping("/species")
	public List<SpeciesDTO> index(){
		List<Species> species = speciesServiceImpl.findAll();
		List<SpeciesDTO> speciesDTO = new ArrayList<SpeciesDTO>();
		species.forEach(s-> speciesDTO.add(getSpeciesDTO(s)));
		return speciesDTO;
		
	}
	
	@GetMapping("/species/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id){
		Species species = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			species = speciesService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(species == null) {
			response.put("mensaje", "La Especie Código: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<SpeciesDTO> (getSpeciesDTO(species), HttpStatus.OK);
	}
	
	
	@PostMapping("/species")
	public ResponseEntity<?> create(@Valid @RequestBody Species species, BindingResult result){
		
		Species newSpecies = null;
		species.setCodigo(HelperFunctions.getLastSpeciesId(speciesServiceImpl));
		
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
			newSpecies = speciesService.save(species);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La Especie ha sido creado con éxito!");
		response.put("people", newSpecies);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/species/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Species species, BindingResult result, @PathVariable Integer id){
		
		Species actualSpecie = speciesService.findById(id);
		Species updatedSpecie = null;
		
		Map<String, Object> response= new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(actualSpecie == null) {
			response.put("mensaje", "Error: no se pudo editar la especie código: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			actualSpecie.setCodigo(species.getCodigo());
			actualSpecie.setPlanets(species.getPlanets());
			actualSpecie.setName(species.getName());
			actualSpecie.setClassification(species.getClassification());
			actualSpecie.setDesignation(species.getDesignation());
			actualSpecie.setAverageHeight(species.getAverageHeight());
			actualSpecie.setAverageLifespan(species.getAverageLifespan());
			actualSpecie.setEyeColors(species.getEyeColors());
			actualSpecie.setHairColors(species.getHairColors());
			actualSpecie.setSkinColors(species.getSkinColors());
			actualSpecie.setLanguage(species.getLanguage());
			actualSpecie.setCreated(species.getCreated());
			actualSpecie.setEdited(species.getEdited());
			actualSpecie.setPeople(species.getPeople());
		
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al conectar con la base de datos");
			response.put("error", e.getMessage().concat(":")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La especie se ha modificado correctamente");
		response.put("specie", updatedSpecie);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/species/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Map<String,Object> response = new HashMap<>();
		try {
			speciesService.delete(id);
		} catch (DataAccessException e) {  // Error al acceder a la base de datos
			response.put("mensaje", "Error al eliminar el id");
			response.put("error", e.getMessage().concat(":")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La especie se ha borrado correctamente");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	
	
	private static SpeciesDTO getSpeciesDTO(Species species) {
		SpeciesDTO speciesDTO = new SpeciesDTO();
		speciesDTO.setCodigo(species.getCodigo());
		if(species.getPlanets() != null)
			speciesDTO.setPlanets(species.getPlanets().getCodigo());
		//System.out.println(species.getPlanets().getCodigo());
		speciesDTO.setName(species.getName());
		speciesDTO.setClassification(species.getClassification());
		speciesDTO.setDesignation(species.getDesignation());
		speciesDTO.setAverageHeight(species.getAverageHeight());
		speciesDTO.setAverageLifespan(species.getAverageLifespan());
		speciesDTO.setEyeColors(species.getEyeColors());
		speciesDTO.setHairColors(species.getHairColors());
		speciesDTO.setSkinColors(species.getSkinColors());
		speciesDTO.setLanguage(species.getLanguage());
		speciesDTO.setCreated(species.getCreated());
		speciesDTO.setEdited(species.getEdited());
		
		List<Integer> codigoPeople = species.getPeople()
											.stream()
											.map(p-> p.getCodigo())
											.collect(Collectors.toList());
		speciesDTO.setPeople(new HashSet<>(codigoPeople));
		
		
		return speciesDTO;
	}
}
