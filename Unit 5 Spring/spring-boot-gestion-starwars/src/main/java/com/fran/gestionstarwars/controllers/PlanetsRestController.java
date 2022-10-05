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

import com.fran.gestionstarwars.entity.Planets;
import com.fran.gestionstarwars.functions.HelperFunctions;
import com.fran.gestionstarwars.model.services.IPlanetsService;
import com.fran.gestionstarwars.model.services.PlanetsServiceImpl;
import com.fran.gestionstarwars.models.dto.PlanetsDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PlanetsRestController {
	
	@Autowired
	private IPlanetsService planetsService;
	
	@Autowired
	private PlanetsServiceImpl planetsServiceImpl;
	
	
	@GetMapping("/planets")
	public List<PlanetsDTO> index(){
		List<Planets> planets = planetsService.findAll();
		List<PlanetsDTO> planetsDTO = new ArrayList<PlanetsDTO>();
		planets.forEach( p-> planetsDTO.add(getPlanetsDTO(p)));
		
		return planetsDTO;
	}
	
	
	@GetMapping("/planets/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id){
		Planets planets = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			planets = planetsService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(planets == null) {
			response.put("mensaje", "El planeta Código: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<PlanetsDTO>( getPlanetsDTO(planets), HttpStatus.OK);
	}
	
	
	
	@PostMapping("/planets")
	public ResponseEntity<?> create(@Valid @RequestBody Planets planets, BindingResult result){
		
		Planets newPlanet = null;
		planets.setCodigo(HelperFunctions.getLastPlanetsId(planetsServiceImpl));
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
			newPlanet = planetsService.save(planets);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El planeta ha sido creado con éxito!");
		response.put("planet", newPlanet);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/planets/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Planets planets, BindingResult result, @PathVariable Integer id){
		
		Planets actualPlanet = planetsService.findById(id);
		Planets updatedPlanet = null;
		
		Map<String, Object> response= new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(actualPlanet == null) {
			response.put("mensaje", "Error: no se pudo editar el planeta código: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			actualPlanet.setName(planets.getName());
			actualPlanet.setDiameter(planets.getDiameter());
			actualPlanet.setRotationPeriod(planets.getRotationPeriod());
			actualPlanet.setOrbitalPeriod(planets.getOrbitalPeriod());
			actualPlanet.setGravity(planets.getGravity());
			actualPlanet.setPopulation(planets.getPopulation());
			actualPlanet.setClimate(planets.getClimate());
			actualPlanet.setTerrain(planets.getTerrain());
			actualPlanet.setSurfaceWater(planets.getSurfaceWater());
			actualPlanet.setCreated(planets.getCreated());
			actualPlanet.setEdited(HelperFunctions.getDate());
			actualPlanet.setSpecies(planets.getSpecies());
			actualPlanet.setPeople(planets.getPeople());
			actualPlanet.setFilms(planets.getFilms());
			
			updatedPlanet = planetsService.save(actualPlanet);
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al conectar con la base de datos");
			response.put("error", e.getMessage().concat(":")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El planeta se ha modificado correctamente");
		response.put("planet", updatedPlanet);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping("/planets/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Map<String,Object> response = new HashMap<>();
		try {
			planetsService.delete(id);
		} catch (DataAccessException e) {  // Error al acceder a la base de datos
			response.put("mensaje", "Error al eliminar el id");
			response.put("error", e.getMessage().concat(":")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El planeta se ha borrado correctamente");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	
	
	private static PlanetsDTO getPlanetsDTO(Planets planets) {
		PlanetsDTO planetsDTO = new PlanetsDTO();
		planetsDTO.setCodigo(planets.getCodigo());
		planetsDTO.setName(planets.getName());
		planetsDTO.setDiameter(planets.getDiameter());
		planetsDTO.setRotationPeriod(planets.getRotationPeriod());
		planetsDTO.setOrbitalPeriod(planets.getOrbitalPeriod());
		planetsDTO.setGravity(planets.getGravity());
		planetsDTO.setPopulation(planets.getPopulation());
		planetsDTO.setClimate(planets.getClimate());
		planetsDTO.setTerrain(planets.getTerrain());
		planetsDTO.setSurfaceWater(planets.getSurfaceWater());
		planetsDTO.setCreated(planets.getCreated());
		planetsDTO.setEdited(planets.getEdited());
		
		List<Integer> codigoSpecies = planets.getSpecies().stream()
								   .map(s-> s.getCodigo())
								   .collect(Collectors.toList());
		
		planetsDTO.setSpecies(new HashSet<>(codigoSpecies));
		
		List<Integer> codigoPeople = planets.getPeople().stream()
								.map(p-> p.getCodigo())
								.collect(Collectors.toList());
		
		planetsDTO.setPeople(new HashSet<>(codigoPeople));
		
		List<Integer> codigoFilms = planets.getFilms().stream()
							      .map(p-> p.getCodigo())
							      .collect(Collectors.toList());
		
		planetsDTO.setFilms(new HashSet<>(codigoFilms));
		return planetsDTO;
	}
}
