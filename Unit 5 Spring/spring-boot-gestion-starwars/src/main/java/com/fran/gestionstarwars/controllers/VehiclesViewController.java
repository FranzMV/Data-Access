package com.fran.gestionstarwars.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fran.gestionstarwars.entity.Vehicles;
import com.fran.gestionstarwars.functions.HelperFunctions;
import com.fran.gestionstarwars.model.services.FilmsServiceImpl;
import com.fran.gestionstarwars.model.services.PeopleServiceImpl;
import com.fran.gestionstarwars.model.services.VehicleServiceImpl;

@Controller
@RequestMapping("/vehicles")
public class VehiclesViewController {

	@Autowired
	private VehicleServiceImpl vehicleServiceImpl;
	@Autowired
	private PeopleServiceImpl peopleServiceImpl;
	@Autowired 
	private FilmsServiceImpl filmsServiceImpl;
	
	
	/**
	 * Vista principal.
	 * @param model Model.
	 * @return ruta vehicles/index.
	 */
	@GetMapping("")
	public String indexVehicles(Model model) {
		model.addAttribute("title", "Opciones Vehículos");
		return "vehicles/index";
	}
	
	/**
	 * Muestra todos los datos de Vehicles en una vista.
	 * @param model Model.
	 * @return ruta vehicles/listar.
	 */
	@GetMapping("/listar")
	public String listarVehicles(Model model) {
		model.addAttribute("title", "Lista de vehículos");
		model.addAttribute("vehicles", vehicleServiceImpl.findAll());
		return "vehicles/listar";
	}
	
	/**
	 * Se encarga de pedir los datos al usuario para crear un nuevo People.
	 * @param model Model.
	 * @return ruta people/anyadir.
	 */
	@GetMapping("/anyadir")
	public String addVehicles(Model model){
		model.addAttribute("title", "Añadir Veículo");
		model.addAttribute("name", "Nombre:");
        model.addAttribute("model", "Modelo:");
		model.addAttribute("vehicleClass", "Clase:");
		model.addAttribute("manufacturer", "Creador:");
		model.addAttribute("length", "Tamaño: ");
		model.addAttribute("costInCredits", "Coste en créditos: ");
		model.addAttribute("crew", "Tripulación: ");
		model.addAttribute("passengers", "Pasajeros: ");
		model.addAttribute("maxAtmospheringSpeed",  "Velocidad máx atmosférica:");
		model.addAttribute("cargoCapacity", "Capacidad de carga: ");
		model.addAttribute("consumables", "Consumibles: ");
		model.addAttribute("edited", "Edición: ");
		model.addAttribute("films", filmsServiceImpl.findAll());
		model.addAttribute("people", peopleServiceImpl.findAll());
		model.addAttribute("vehicles", new Vehicles());
		return "vehicles/anyadir";
	}
	
	/**
	 * Se encarga de crear un nuevo Vehicle generado en la vista anyadir.
	 * @param people Objeto de tipo Vehicle.
	 * @param result BindingResult resultado de la operación.
	 * @param mod Model.
	 * @return ModelAndView.
	 */
	@RequestMapping("/create")
	public ModelAndView createVehicle(@Valid Vehicles vehicles, BindingResult result, Model mod) {
		ModelAndView model = new ModelAndView();
		boolean exists = false;
		vehicles.setCreated(HelperFunctions.getDate());
		vehicles.setEdited(HelperFunctions.getDate());
		vehicles.setCodigo(HelperFunctions.getLastVechiclesId(vehicleServiceImpl));
		model.addObject("vehicles", vehicles);

		if (!result.hasErrors()) {
			for (Vehicles v : vehicleServiceImpl.findAll()) {
				if (v.getCodigo() == vehicles.getCodigo()) {
					exists = true;
					break;
				}
			}
			model.setViewName("ready");
			if (!exists) {
				mod.addAttribute("resultado", "Vehículo creado");
				mod.addAttribute("option", 3);
				vehicleServiceImpl.save(vehicles);
			} else {
				mod.addAttribute("resultado", "El código de vehículo ya existe");
				mod.addAttribute("option", 3);
			}
		} else
			model.setViewName("vehicles/editar");
		return model;
	}
	
	/**
	 * Se encarga de mostrar los datos en la vista de editar y recoger los cambios realizados por el usuario.
	 * @param codigo Integer correspondiente al código del vehículo a editar.
	 * @param model Model.
	 * @return ruta vehicles/editar.
	 */
	@GetMapping("/edit/{codigo}")
	public String editVehicle( @PathVariable("codigo") Integer codigo, Model model) {
		Vehicles updatedVehicle = vehicleServiceImpl.findById(codigo);
		updatedVehicle.setEdited(HelperFunctions.getDate());
		model.addAttribute("title", "Editar Vehículos");
		model.addAttribute("vehicles", updatedVehicle);
		model.addAttribute("people", peopleServiceImpl.findAll());
		model.addAttribute("films", filmsServiceImpl.findAll());
		return "vehicles/editar";
		
	}
	
	
	/**
	 * Se encarga de editar el registro de tipo People recibido como parámetro por las vista.
	 * @param people Objeto de tipo People a editar.
	 * @param result ResultBinding resultado de la operación
	 * @param mod Model
	 * @return model ModelAndView vista con el resultado de la operación
	 */
	@RequestMapping("/update")
	public ModelAndView updateVehicle(@Valid Vehicles vehicles, BindingResult result, Model mod) {
		ModelAndView model = new ModelAndView();
		model.addObject("vehicles", vehicles);
		if (!result.hasErrors()) {
			model.setViewName("ready");
			mod.addAttribute("resultado", "Vehículo editado");
			mod.addAttribute("option", 3);
			vehicleServiceImpl.save(vehicles);
			
		} else
			model.setViewName("vehicles/editar");

		return model;
	}
	
	/**
	 * Se encarga de eliminar un registro de tipo Vehicle cuyo código coincide con el recibido como parámetro.
	 * @param codigo Integer codigo del registro a eliminar
	 * @param model Model.
	 * @return vista con el resultado de la operación
	 */
	@GetMapping("/delete/{codigo}")
	public String deleteVehicle(@PathVariable("codigo") Integer codigo, Model model) {
		vehicleServiceImpl.delete(codigo);
		model.addAttribute("resultado", "Vehículo borrado");
		model.addAttribute("option", 3);
		return "ready";
	}
}
