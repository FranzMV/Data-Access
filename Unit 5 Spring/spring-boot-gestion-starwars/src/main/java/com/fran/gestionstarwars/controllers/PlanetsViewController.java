package com.fran.gestionstarwars.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.fran.gestionstarwars.entity.Planets;
import com.fran.gestionstarwars.functions.HelperFunctions;
import com.fran.gestionstarwars.model.services.FilmsServiceImpl;
import com.fran.gestionstarwars.model.services.PeopleServiceImpl;
import com.fran.gestionstarwars.model.services.PlanetsServiceImpl;
import com.fran.gestionstarwars.model.services.SpeciesServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/planets")
public class PlanetsViewController {
	
	@Autowired
	private PlanetsServiceImpl planetsServiceImpl;
	
	@Autowired
	private FilmsServiceImpl filmsServiceImpl;
	
	@Autowired
	private PeopleServiceImpl peopleServiceImpl;
	
	@Autowired
	private SpeciesServiceImpl speciesServiceImpl;
	
	/**
	 * Vista principal.
	 * @param model Model.
	 * @return ruta planets/index.
	 */
	@GetMapping("")
	public String indexPlanets(Model model) {
		model.addAttribute("title", "Opciones Planets");
		return "planets/index";
	}
	
	
	/**
	 * Muestra todos los datos de Planets en una vista.
	 * @param model Model.
	 * @return ruta planets/listar.
	 */
	@GetMapping("/listar")
	public String listarPlanets(Model model) {
		model.addAttribute("title", "Lista de planetas");
		model.addAttribute("planets", planetsServiceImpl.findAll());
		planetsServiceImpl.findAll().forEach(p-> System.out.println(p.getName()));
		return "planets/listar";
	}
	
	/**
	 * Se encarga de pedir los datos al usuario para crear un nuevo People.
	 * @param model Model.
	 * @return ruta people/anyadir.
	 */
	@GetMapping("/anyadir")
	public String addPlanet(Model model){
		model.addAttribute("title", "Añadir planeta");
		model.addAttribute("diameter", "Diámetro:");
        model.addAttribute("rotationPeriod", "Periodo de rotación:");
		model.addAttribute("orbitalPeriod", "Periodo orbital:");
		model.addAttribute("gravity", "Gravedad:");
		model.addAttribute("population", "Población:");
		model.addAttribute("climate", "Clima:");
		model.addAttribute("terrain", "Terreno:");
		model.addAttribute("surfaceWater", "Superficie de agua:");
		model.addAttribute("edited", "Edición:");
		model.addAttribute("species", speciesServiceImpl.findAll());
		model.addAttribute("people", peopleServiceImpl.findAll());
		model.addAttribute("films", filmsServiceImpl.findAll());
		model.addAttribute("planets", new Planets());
		return "planets/anyadir";
	}
	
	/**
	 * Se encarga de crear un nuevo Planet generado en la vista anyadir.
	 * @param planets Objeto de tipo Planet.
	 * @param result BindingResult resultado de la operación.
	 * @param mod Model.
	 * @return ModelAndView.
	 */
	@RequestMapping("/create")
	public ModelAndView createPeople(@Valid Planets planets, BindingResult result, Model mod) {
		ModelAndView model = new ModelAndView();
		boolean exists = false;
		planets.setCreated(HelperFunctions.getDate());
		planets.setEdited(HelperFunctions.getDate());
		planets.setCodigo(HelperFunctions.getLastPlanetsId(planetsServiceImpl));
		model.addObject("planets", planets);

		if (!result.hasErrors()) {
			for (Planets p : planetsServiceImpl.findAll()) {
				if (p.getCodigo() == planets.getCodigo()) {
					exists = true;
					break;
				}
			}
			model.setViewName("ready");
			if (!exists) {
				mod.addAttribute("resultado", "Planeta creado");
				mod.addAttribute("option", 5);
				planetsServiceImpl.save(planets);
			} else {
				mod.addAttribute("resultado", "El código de planeta ya existe");
				mod.addAttribute("option", 5);
			}
		} else
			model.setViewName("planets/editar");
		return model;
	}
	
	
	/**
	 * Se encarga de mostrar los datos en la vista de editar y recoger los cambios realizados por el usuario.
	 * @param codigo Integer correspondiente al código del Planeta a editar.
	 * @param model Model.
	 * @return ruta planets/editar.
	 */
	@GetMapping("/edit/{codigo}")
	public String editPlanets( @PathVariable("codigo") Integer codigo, Model model) {
		Planets updatedPlanet = planetsServiceImpl.findById(codigo);
		updatedPlanet.setEdited(HelperFunctions.getDate());
		model.addAttribute("title", "Editar planeta");
		model.addAttribute("planets", updatedPlanet);
		model.addAttribute("people", peopleServiceImpl.findAll());
		model.addAttribute("species", speciesServiceImpl.findAll());
		model.addAttribute("films", filmsServiceImpl.findAll());
		return "planets/editar";
		
	}
	
	/**
	 * Se encarga de editar el registro de tipo Planet recibido como parámetro por las vista.
	 * @param planets Objeto de tipo Planets a editar.
	 * @param result ResultBinding resultado de la operación
	 * @param mod Model
	 * @return model ModelAndView vista con el resultado de la operación
	 */
	@RequestMapping("/update")
	public ModelAndView updatePlanets(@Valid Planets planets, BindingResult result, Model mod) {
		ModelAndView model = new ModelAndView();
		model.addObject("planets", planets);
		if (!result.hasErrors()) {
			model.setViewName("ready");
			mod.addAttribute("resultado", "Planeta editado");
			mod.addAttribute("option", 5);
			planetsServiceImpl.save(planets);
			
		} else
			model.setViewName("planets/editar");

		return model;
	}
	
	/**
	 * Se encarga de eliminar un registro de tipo Planet cuyo código coincide con el recibido como parámetro.
	 * @param codigo Integer codigo del registro a eliminar
	 * @param model Model.
	 * @return vista con el resultado de la operación
	 */
	@GetMapping("/delete/{codigo}")
	public String deletePeople(@PathVariable("codigo") Integer codigo, Model model) {
		planetsServiceImpl.delete(codigo);
		model.addAttribute("resultado", "Planeta borrado");
		model.addAttribute("option", 5);
		return "ready";
	}
	
}
