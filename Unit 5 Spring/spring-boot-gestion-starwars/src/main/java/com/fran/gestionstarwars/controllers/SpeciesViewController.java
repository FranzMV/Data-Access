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

import com.fran.gestionstarwars.entity.Species;
import com.fran.gestionstarwars.functions.HelperFunctions;
import com.fran.gestionstarwars.model.services.PeopleServiceImpl;
import com.fran.gestionstarwars.model.services.PlanetsServiceImpl;
import com.fran.gestionstarwars.model.services.SpeciesServiceImpl;


@Controller
@RequestMapping("/species")
public class SpeciesViewController {
	
	@Autowired
	private SpeciesServiceImpl speciesServiceImpl;
	
	@Autowired
	private PlanetsServiceImpl planetsServiceImpl;
	
	@Autowired
	private PeopleServiceImpl peopleServiceImpl;

	
	/**
	 * Vista principal.
	 * @param model Model.
	 * @return ruta species/index.
	 */
	@GetMapping("")
	public String indexSpecies(Model model) {
		model.addAttribute("title", "Opciones Especies");
		return "species/index";
	}
	
	/**
	 * Muestra todos los datos de Especies en una vista.
	 * @param model Model.
	 * @return ruta species/listar.
	 */
	@GetMapping("/listar")
	public String listarSpecies(Model model) {
		model.addAttribute("title", "Lista de especies");
		model.addAttribute("species",speciesServiceImpl.findAll());
		return "species/listar";
	}
	
	
	/**
	 * Se encarga de pedir los datos al usuario para crear un nuevo People.
	 * @param model Model.
	 * @return ruta people/anyadir.
	 */
	@GetMapping("/anyadir")
	public String addSpecies(Model model){
	        model.addAttribute("title", "Añadir especie");
		model.addAttribute("name", "Nombre:");
        	model.addAttribute("classification", "Clasificación:");
        	model.addAttribute("designation", "Designación:");
        	model.addAttribute("averageHeight", "Media de altura:");
        	model.addAttribute("averageLifespan", "Esperanza de vida:");
		model.addAttribute("eyeColors", "Color de ojos:");
		model.addAttribute("skinColors", "Color de piel:");
		model.addAttribute("hairColors", "Color de pelo:");
		model.addAttribute("language", "Lenguaje:");
		model.addAttribute("edited", "Edición:");
		model.addAttribute("planets", planetsServiceImpl.findAll());
		model.addAttribute("species", new Species());
		model.addAttribute("people", peopleServiceImpl.findAll());
		return "species/anyadir";
	}
	
	/**
	 * Se encarga de crear un nuevo People generado en la vista anyadir.
	 * @param people Objeto de tipo People.
	 * @param result BindingResult resultado de la operación.
	 * @param mod Model.
	 * @return ModelAndView.
	 */
	@RequestMapping("/create")
	public ModelAndView createSpecie(@Valid Species species, BindingResult result, Model mod) {
		ModelAndView model = new ModelAndView();
		boolean exists = false;
		species.setCreated(HelperFunctions.getDate());
		species.setEdited(HelperFunctions.getDate());
		species.setCodigo(HelperFunctions.getLastSpeciesId(speciesServiceImpl));
		model.addObject("species", species);

		if (!result.hasErrors()) {
			for (Species s : speciesServiceImpl.findAll()) {
				if (s.getCodigo() == species.getCodigo()) {
					exists = true;
					break;
				}
			}
			model.setViewName("ready");
			if (!exists) {
				mod.addAttribute("resultado", "Especie creada");
				mod.addAttribute("option", 4);
				speciesServiceImpl.save(species);
			} else {
				mod.addAttribute("resultado", "El código de especie ya existe");
				mod.addAttribute("option", 4);
			}
		} else
			model.setViewName("species/editar");
		return model;
	}
	
	/**
	 * Se encarga de mostrar los datos en la vista de editar y recoger los cambios realizados por el usuario.
	 * @param codigo Integer correspondiente al código de la Specie a editar.
	 * @param model Model.
	 * @return ruta species/editar.
	 */
	@GetMapping("/edit/{codigo}")
	public String editSpecie( @PathVariable("codigo") Integer codigo, Model model) {
		Species updatedSpecie = speciesServiceImpl.findById(codigo);
		updatedSpecie.setEdited(HelperFunctions.getDate());
		model.addAttribute("title", "Editar especie");
		model.addAttribute("species", updatedSpecie);
		model.addAttribute("planets", planetsServiceImpl.findAll());
		model.addAttribute("people", peopleServiceImpl.findAll());
		return "species/editar";
		
	}
	
	/**
	 * Se encarga de editar el registro de tipo Specie recibido como parámetro por las vista.
	 * @param people Objeto de tipo Specie a editar.
	 * @param result ResultBinding resultado de la operación
	 * @param mod Model
	 * @return model ModelAndView vista con el resultado de la operación
	 */
	@RequestMapping("/update")
	public ModelAndView updateSpecie(@Valid Species species, BindingResult result, Model mod) {
		ModelAndView model = new ModelAndView();
		model.addObject("species", species);
		if (!result.hasErrors()) {
			model.setViewName("ready");
			mod.addAttribute("resultado", "Especie editada");
			mod.addAttribute("option", 4);
			speciesServiceImpl.save(species);
			
		} else
			model.setViewName("species/editar");

		return model;
	}
	
	/**
	 * Se encarga de eliminar un registro de tipo Specie cuyo código coincide con el recibido como parámetro.
	 * @param codigo Integer codigo del registro a eliminar
	 * @param model Model.
	 * @return vista con el resultado de la operación
	 */
	@GetMapping("/delete/{codigo}")
	public String deleteSpecie(@PathVariable("codigo") Integer codigo, Model model) {
		speciesServiceImpl.delete(codigo);
		model.addAttribute("resultado", "Especie borrada");
		model.addAttribute("option", 4);
		return "ready";
	}
	
}
