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

import com.fran.gestionstarwars.entity.People;
import com.fran.gestionstarwars.functions.HelperFunctions;
import com.fran.gestionstarwars.model.services.FilmsServiceImpl;
import com.fran.gestionstarwars.model.services.PeopleServiceImpl;
import com.fran.gestionstarwars.model.services.PlanetsServiceImpl;
import com.fran.gestionstarwars.model.services.SpeciesServiceImpl;
import com.fran.gestionstarwars.model.services.StarshipServiceImpl;
import com.fran.gestionstarwars.model.services.VehicleServiceImpl;


@Controller
@RequestMapping("/people")
public class PeopleViewController {
	
	
	@Autowired
	private PeopleServiceImpl peopleServiceImpl;
	
	@Autowired
	private FilmsServiceImpl filmsServiceImpl;
	
	@Autowired
	private PlanetsServiceImpl planetsServiceImpl;
	
	@Autowired
	private StarshipServiceImpl starshipServiceImpl;
	
	@Autowired
	private SpeciesServiceImpl speciesServiceImpl;
	
	@Autowired
	private VehicleServiceImpl vehicleServiceImpl;
	
	
	
	/**
	 * Vista principal.
	 * @param model Model.
	 * @return ruta people/index.
	 */
	@GetMapping("")
	public String indexPeople(Model model) {
		model.addAttribute("title", "Opciones Personajes");
		return "people/index";
	}
	
	/**
	 * Muestra todos los datos de People en una vista.
	 * @param model Model.
	 * @return ruta people/listar.
	 */
	@GetMapping("/listar")
	public String listarPeople(Model model) {
		model.addAttribute("title", "Lista de personajes");
		model.addAttribute("people", peopleServiceImpl.findAll());
		return "people/listar";
	}
	
	/**
	 * Se encarga de pedir los datos al usuario para crear un nuevo People.
	 * @param model Model.
	 * @return ruta people/anyadir.
	 */
	@GetMapping("/anyadir")
	public String addPeople(Model model){
		model.addAttribute("title", "Añadir personaje");
		model.addAttribute("name", "Nombre:");
        model.addAttribute("bithyear", "Fecha naciemiento:");
		model.addAttribute("eyeColor", "Color de ojos:");
		model.addAttribute("skinColor", "Color de piel:");
		model.addAttribute("hairColor", "Color de pelo:");
		model.addAttribute("gender", "Género:");
		model.addAttribute("height", "Altura:");
		model.addAttribute("mass", "Peso:");
		model.addAttribute("edited", "Edición:");
		model.addAttribute("planets", planetsServiceImpl.findAll());
		model.addAttribute("starships", starshipServiceImpl.findAll());
		model.addAttribute("vehicles", vehicleServiceImpl.findAll());
		model.addAttribute("species", speciesServiceImpl.findAll());
		model.addAttribute("films", filmsServiceImpl.findAll());
		model.addAttribute("people", new People());
		return "people/anyadir";
	}
	
	/**
	 * Se encarga de crear un nuevo People generado en la vista anyadir.
	 * @param people Objeto de tipo People.
	 * @param result BindingResult resultado de la operación.
	 * @param mod Model.
	 * @return ModelAndView.
	 */
	@RequestMapping("/create")
	public ModelAndView createPeople(@Valid People people, BindingResult result, Model mod) {
		ModelAndView model = new ModelAndView();
		boolean exists = false;
		people.setCreated(HelperFunctions.getDate());
		people.setEdited(HelperFunctions.getDate());
		people.setCodigo(HelperFunctions.getLastPeopleId(peopleServiceImpl));
		model.addObject("people", people);

		if (!result.hasErrors()) {
			for (People p : peopleServiceImpl.findAll()) {
				if (p.getCodigo() == people.getCodigo()) {
					exists = true;
					break;
				}
			}
			model.setViewName("ready");
			if (!exists) {
				mod.addAttribute("resultado", "Personaje creado");
				mod.addAttribute("option", 1);
				peopleServiceImpl.save(people);
			} else {
				mod.addAttribute("resultado", "El código de personaje ya existe");
				mod.addAttribute("option", 1);
			}
		} else
			model.setViewName("people/editar");
		return model;
	}
	
	
	/**
	 * Se encarga de mostrar los datos en la vista de editar y recoger los cambios realizados por el usuario.
	 * @param codigo Integer correspondiente al código del Personaje a editar.
	 * @param model Model.
	 * @return ruta people/editar.
	 */
	@GetMapping("/edit/{codigo}")
	public String editPeople( @PathVariable("codigo") Integer codigo, Model model) {
		People updatedPeople = peopleServiceImpl.findById(codigo);
		updatedPeople.setEdited(HelperFunctions.getDate());
		model.addAttribute("title", "Editar personaje");
		model.addAttribute("people", updatedPeople);
		model.addAttribute("planets", planetsServiceImpl.findAll());
		model.addAttribute("starships", starshipServiceImpl.findAll());
		model.addAttribute("vehicles", vehicleServiceImpl.findAll());
		model.addAttribute("species", speciesServiceImpl.findAll());
		model.addAttribute("films", filmsServiceImpl.findAll());
		return "people/editar";
		
	}
	
	
	/**
	 * Se encarga de editar el registro de tipo People recibido como parámetro por las vista.
	 * @param people Objeto de tipo People a editar.
	 * @param result ResultBinding resultado de la operación
	 * @param mod Model
	 * @return model ModelAndView vista con el resultado de la operación
	 */
	@RequestMapping("/update")
	public ModelAndView updatePeople(@Valid People people, BindingResult result, Model mod) {
		ModelAndView model = new ModelAndView();
		model.addObject("people", people);
		if (!result.hasErrors()) {
			model.setViewName("ready");
			mod.addAttribute("resultado", "Personaje editado");
			mod.addAttribute("option", 1);
			peopleServiceImpl.save(people);
			
		} else
			model.setViewName("people/editar");

		return model;
	}
	
	
	/**
	 * Se encarga de eliminar un registro de tipo People cuyo código coincide con el recibido como parámetro.
	 * @param codigo Integer codigo del registro a eliminar
	 * @param model Model.
	 * @return vista con el resultado de la operación
	 */
	@GetMapping("/delete/{codigo}")
	public String deletePeople(@PathVariable("codigo") Integer codigo, Model model) {
		peopleServiceImpl.delete(codigo);
		model.addAttribute("resultado", "Personaje borrado");
		model.addAttribute("option", 1);
		return "ready";
	}
}
