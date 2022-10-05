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

import com.fran.gestionstarwars.entity.Starships;
import com.fran.gestionstarwars.functions.HelperFunctions;
import com.fran.gestionstarwars.model.services.IStarshipService;
import com.fran.gestionstarwars.model.services.StarshipServiceImpl;
import com.fran.gestionstarwars.models.dto.StarshipsDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class StarshipsRestController {

	@Autowired
	private IStarshipService starshipService;
	
	@Autowired
	private StarshipServiceImpl starshipServiceImpl;
	
	
	@GetMapping("/starships")
	public List<StarshipsDTO> index(){
		List<Starships> starships = starshipService.findAll();
		List<StarshipsDTO> starshipsDTO = new ArrayList<>();
		starships.forEach(s-> starshipsDTO.add(getStarshipDTO(s)));
		
		return starshipsDTO;
	}
	
	@GetMapping("/starships/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id){
		Starships starship = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			starship = starshipService.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(starship == null) {
			response.put("mensaje", "La nave Código: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<StarshipsDTO>(getStarshipDTO(starship), HttpStatus.OK);
	}
	
	
	@PostMapping("/starships")
	public ResponseEntity<?> create(@Valid @RequestBody Starships starship, BindingResult result){
		
		Starships newStarship = null;
		starship.setCodigo(HelperFunctions.getLastStarshipsId(starshipServiceImpl));
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
			newStarship = starshipService.save(starship);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La nave ha sido creado con éxito!");
		response.put("starship", newStarship);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/starships/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Starships starship, BindingResult result, @PathVariable Integer id){
		
		Starships actualStarship = starshipServiceImpl.findById(id);
		Starships updateStarship = null;
		
		Map<String, Object> response= new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(actualStarship == null) {
			response.put("mensaje", "Error: no se pudo editar la nave con código: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			actualStarship.setName(starship.getName());
			actualStarship.setModel(starship.getModel());
			actualStarship.setStarshipClass(starship.getStarshipClass());
			actualStarship.setManufacturer(starship.getManufacturer());
			actualStarship.setCostInCredits(starship.getCostInCredits());
			actualStarship.setLength(starship.getLength());
			actualStarship.setCrew(starship.getCrew());
			actualStarship.setPassengers(starship.getPassengers());
			actualStarship.setMaxAtmospheringSpeed(starship.getMaxAtmospheringSpeed());
			actualStarship.setHyperdriveRating(starship.getHyperdriveRating());
			actualStarship.setMglt(starship.getMglt());
			actualStarship.setCargoCapacity(starship.getCargoCapacity());
			actualStarship.setConsumables(starship.getConsumables());
			actualStarship.setCreated(starship.getCreated());
			actualStarship.setEdited(HelperFunctions.getDate());
			actualStarship.setFilms(starship.getFilms());
			actualStarship.setPeoples(starship.getPeople());
			
			updateStarship = starshipService.save(actualStarship);
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al conectar con la base de datos");
			response.put("error", e.getMessage().concat(":")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La nave se ha modificado correctamente");
		response.put("starship", updateStarship);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	
	
	
	@DeleteMapping("/starships/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Map<String,Object> response = new HashMap<>();
		try {
			starshipService.delete(id);
		} catch (DataAccessException e) {  // Error al acceder a la base de datos
			response.put("mensaje", "Error al eliminar el id");
			response.put("error", e.getMessage().concat(":")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La nave se ha borrado correctamente");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	
	
	private static StarshipsDTO getStarshipDTO(Starships starship) {
		StarshipsDTO starshipDTO = new StarshipsDTO();
		starshipDTO.setCodigo(starship.getCodigo());
		starshipDTO.setName(starship.getName());
		starshipDTO.setModel(starship.getModel());
		starshipDTO.setStarshipClass(starship.getStarshipClass());
		starshipDTO.setManufacturer(starship.getManufacturer());
		starshipDTO.setCostInCredits(starship.getCostInCredits());
		starshipDTO.setLength(starship.getLength());
		starshipDTO.setCrew(starship.getCrew());
		starshipDTO.setPassengers(starship.getPassengers());
		starshipDTO.setMaxAtmospheringSpeed(starship.getMaxAtmospheringSpeed());
		starshipDTO.setHyperdriveRating(starship.getHyperdriveRating());
		starshipDTO.setMglt(starship.getMglt());
		starshipDTO.setCargoCapacity(starship.getCargoCapacity());
		starshipDTO.setConsumables(starship.getConsumables());
		starshipDTO.setCreated(starship.getCreated());
		starshipDTO.setEdited(starship.getEdited());
		
		List<Integer> codigoFilms = starship.getFilms().stream()
								.map(f-> f.getCodigo())
								.collect(Collectors.toList());
		
		starshipDTO.setFilms(new HashSet<>(codigoFilms));
		
		List<Integer> codigoPeople = starship.getPeople().stream()
								  .map(p-> p.getCodigo())
								  .collect(Collectors.toList());

		starshipDTO.setPeople(new HashSet<>(codigoPeople));
		
		return starshipDTO;
	}
}
