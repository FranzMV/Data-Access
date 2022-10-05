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

import com.fran.gestionstarwars.entity.Vehicles;
import com.fran.gestionstarwars.functions.HelperFunctions;
import com.fran.gestionstarwars.model.services.IVehiclesService;
import com.fran.gestionstarwars.model.services.VehicleServiceImpl;
import com.fran.gestionstarwars.models.dto.VehiclesDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class VehiclesRestController {

	
	@Autowired
	private IVehiclesService vehiclesService;
	@Autowired
	private VehicleServiceImpl vehicleServiceImpl;
	
	@GetMapping("/vehicles")
	public List<VehiclesDTO> index(){
		List<Vehicles> vehiclesList = vehicleServiceImpl.findAll();
		List<VehiclesDTO> vehiclesDTO = new ArrayList<VehiclesDTO>();
		vehiclesList.forEach( v-> vehiclesDTO.add(getVehicleDTO(v)));
		
		return vehiclesDTO;
	}
	
	
	@GetMapping("/vehicles/{id}")
	public ResponseEntity<?> show(@PathVariable Integer id){
		Vehicles vehicles = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			vehicles = vehicleServiceImpl.findById(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(vehicles == null) {
			response.put("mensaje", "El vehículo Código: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<VehiclesDTO>( getVehicleDTO(vehicles), HttpStatus.OK);
	}
	
	@PostMapping("/vehicles")
	public ResponseEntity<?> create(@Valid @RequestBody Vehicles vehicles, BindingResult result){
		
		Vehicles newVehicle = null;
		vehicles.setCodigo(HelperFunctions.getLastVechiclesId(vehicleServiceImpl));
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
			newVehicle= vehiclesService.save(vehicles);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El vehículo ha sido creado con éxito!");
		response.put("vehicle", newVehicle);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/vehicles/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Vehicles vehicles, BindingResult result, @PathVariable Integer id){
		
		Vehicles actualVehicle = vehiclesService.findById(id);
		Vehicles updatedVehicle = null;
		
		Map<String, Object> response= new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(actualVehicle == null) {
			response.put("mensaje", "Error: no se pudo editar el vehículo código: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			actualVehicle.setName(vehicles.getName());
			actualVehicle.setModel(vehicles.getModel());
			actualVehicle.setVehicleClass(vehicles.getVehicleClass());
			actualVehicle.setManufacturer(vehicles.getManufacturer());
			actualVehicle.setLength(vehicles.getLength());
			actualVehicle.setCostInCredits(vehicles.getCostInCredits());
			actualVehicle.setCrew(vehicles.getCrew());
			actualVehicle.setPassengers(vehicles.getPassengers());
			actualVehicle.setMaxAtmospheringSpeed(vehicles.getMaxAtmospheringSpeed());
			actualVehicle.setCargoCapacity(vehicles.getCargoCapacity());
			actualVehicle.setConsumables(vehicles.getConsumables());
			actualVehicle.setEdited(vehicles.getEdited());
			actualVehicle.setCreated(vehicles.getCreated());
			actualVehicle.setFilms(vehicles.getFilms());
			actualVehicle.setPeoples(vehicles.getPeople());
			
			updatedVehicle = vehiclesService.save(actualVehicle);
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al conectar con la base de datos");
			response.put("error", e.getMessage().concat(":")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El vehiculo se ha modificado correctamente");
		response.put("vehicle", updatedVehicle);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/vehicles/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Map<String,Object> response = new HashMap<>();
		try {
			vehiclesService.delete(id);
		} catch (DataAccessException e) {  // Error al acceder a la base de datos
			response.put("mensaje", "Error al eliminar el id");
			response.put("error", e.getMessage().concat(":")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El vehículo se ha borrado correctamente");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	
	
	
	private static VehiclesDTO getVehicleDTO(Vehicles vehicles) {
		VehiclesDTO vehiclesDTO = new VehiclesDTO();
		vehiclesDTO.setCodigo(vehicles.getCodigo());
		vehiclesDTO.setName(vehicles.getName());
		vehiclesDTO.setModel(vehicles.getModel());
		vehiclesDTO.setVehicleClass(vehicles.getVehicleClass());
		vehiclesDTO.setManufacturer(vehicles.getManufacturer());
		vehiclesDTO.setLength(vehicles.getLength());
		vehiclesDTO.setCostInCredits(vehicles.getCostInCredits());
		vehiclesDTO.setCrew(vehicles.getCrew());
		vehiclesDTO.setPassengers(vehicles.getPassengers());
		vehiclesDTO.setMaxAtmospheringSpeed(vehicles.getMaxAtmospheringSpeed());
		vehiclesDTO.setCargoCapacity(vehicles.getCargoCapacity());
		vehiclesDTO.setConsumables(vehicles.getConsumables());
		vehiclesDTO.setEdited(vehicles.getEdited());
		vehiclesDTO.setCreated(vehicles.getCreated());
		
		List<Integer> codigoFilms = vehicles.getFilms() .stream()
								.map(f-> f.getCodigo())
								.collect(Collectors.toList());
		
		vehiclesDTO.setFilms(new HashSet<>(codigoFilms));
		
		List<Integer> codigoPeople = vehicles.getPeople().stream()
								  .map(p-> p.getCodigo())
								  .collect(Collectors.toList());
		
		vehiclesDTO.setPeople(new HashSet<>(codigoPeople));
		
		return vehiclesDTO;
	}
}
