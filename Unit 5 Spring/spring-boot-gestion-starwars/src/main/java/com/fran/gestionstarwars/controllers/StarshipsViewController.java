package com.fran.gestionstarwars.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fran.gestionstarwars.entity.Starships;
import com.fran.gestionstarwars.functions.HelperFunctions;
import com.fran.gestionstarwars.model.services.FilmsServiceImpl;
import com.fran.gestionstarwars.model.services.PeopleServiceImpl;
import com.fran.gestionstarwars.model.services.StarshipServiceImpl;

@Controller
@RequestMapping("/starships")
public class StarshipsViewController {

	
	@Autowired
	private StarshipServiceImpl starshipServiceImpl;
	
	@Autowired
	private PeopleServiceImpl peopleServiceImpl;
	
	@Autowired
	private FilmsServiceImpl filmsServiceImpl;
	
	
	/**
	 * Vista del menú principal Starships.
	 * @param model Model.
	 * @return ruta starships/index.
	 */
	@GetMapping("")
	public String indexStarships(Model model) {
		model.addAttribute("title", "Opciones naves");
		return "starships/index";
	}
	
	/**
	 * Muestra todos los datos de Starships en una vista.
	 * @param model Model.
	 * @return ruta starships/listar.
	 */
	@GetMapping("/listar")
	public String listarStarships(Model model) {
		model.addAttribute("title", "Lista de naves");
		model.addAttribute("starships", starshipServiceImpl.findAll());
		return "starships/listar";
	}
	
	
	/**
	 * Se encarga de pedir los datos al usuario para crear un nuevo Starship.
	 * @param model Model.
	 * @return ruta starships/anyadir.
	 */
	@GetMapping("/anyadir")
	public String addStarship(Model model) {
		model.addAttribute("title", "Añadir nave");
		model.addAttribute("name", "Nombre: ");
		model.addAttribute("model", "Modelo: ");
		model.addAttribute("starshipClass", "Clase: ");
		model.addAttribute("manufacturer", "Creador: ");
		model.addAttribute("costInCredits", "Coste en créditos: ");
		model.addAttribute("length", "Tamaño: ");
		model.addAttribute("crew", "Tripulación: ");
		model.addAttribute("passengers", "Pasajeros: ");
		model.addAttribute("maxAtmospheringSpeed",  "Velocidad máx atmosférica:");
		model.addAttribute("hyperdriveRating", "Rango de Hyperdirve: ");
		model.addAttribute("mglt", "MGLT: ");
		model.addAttribute("cargoCapacity", "Capacidad de carga: ");
		model.addAttribute("consumables", "Consumibles: ");
		model.addAttribute("edited", "Edición: ");
		model.addAttribute("films", filmsServiceImpl.findAll());
		model.addAttribute("people", peopleServiceImpl.findAll());
		model.addAttribute("starships", new Starships());
		return"starships/anyadir";
	}
	
	/**
	 * Se encarga de crear una nueva Nave generada en la vista anyadir.
	 * @param starship Objeto de tipo Starships.
	 * @param result BindingResult resultado de la operación.
	 * @param mod Model.
	 * @return ModelAndView.
	 */
	@RequestMapping("/create")
	@Transactional
	public ModelAndView createStarship(@Valid Starships starships, BindingResult result, Model mod) {
		ModelAndView model = new ModelAndView();
		boolean exists = false;
		
		starships.setCreated(HelperFunctions.getDate());
		starships.setEdited(HelperFunctions.getDate());
		starships.setCodigo(HelperFunctions.getLastStarshipsId(starshipServiceImpl));
		model.addObject("starships", starships);
		
		if (!result.hasErrors()) {
			for (Starships s : starshipServiceImpl.findAll()) {
				if (s.getCodigo() == starships.getCodigo()) {
					exists = true;
					break;
				}
			}
			model.setViewName("ready");
			if (!exists) {
				mod.addAttribute("resultado", "Nave creada");
				mod.addAttribute("option", 2);
				starshipServiceImpl.save(starships);
				System.out.println("Tamanyo "+starships.getPeople().size());
				System.out.println("Tamanyo "+starships.getFilms().size());
			} else {
				mod.addAttribute("resultado", "El código de nave ya existe");
				mod.addAttribute("option", 2);
			}
		} else
			model.setViewName("starships/editar");
		return model;
	}
	
	
	/**
	 * Se encarga de mostrar los datos en la vista de editar y recoger los cambios realizados por el usuario.
	 * @param codigo Integer correspondiente al código de la Nave a editar.
	 * @param model Model.
	 * @return ruta starships/editar.
	 */
	@GetMapping("/edit/{codigo}")
	public String editStarship(@PathVariable("codigo") Integer codigo, Model model) {
		Starships updateStarship = starshipServiceImpl.findById(codigo);
		updateStarship.setEdited(HelperFunctions.getDate());
		model.addAttribute("title", "Editar nave");
		model.addAttribute("starship", updateStarship);
		model.addAttribute("films", filmsServiceImpl.findAll());
		model.addAttribute("people", peopleServiceImpl.findAll());
		
		return"starships/editar";
	}
	
	
	/**
	 * Se encarga de editar el registro de tipo Starship recibido como parámetro por las vista.
	 * @param starship objeto de tipo Starships a editar.
	 * @param result BindingResult resultado de la operación.
	 * @param mod Model.
	 * @return model ModelAndView vista con el resultado de la operación.
	 */
	@RequestMapping("/update")
	public ModelAndView updateStarship(@Valid Starships starship, BindingResult result, Model mod) {
		ModelAndView model = new ModelAndView();
		model.addObject("starship", starship);
		if (!result.hasErrors()) {
			model.setViewName("ready");
			mod.addAttribute("resultado", "Nave editada");
			mod.addAttribute("option", 2);
			starshipServiceImpl.save(starship);
			
		} else
			model.setViewName("starships/editar");

		return model;
	}
	
	
	/**
	 * Se encarga de eliminar un registro de tipo Starships cuyo código coincide con el recibido como parámetro.
	 * @param codigo Integer codigo del registro a eliminar.
	 * @param model Model.
	 * @return vista con el resultado de la operación
	 */
	@GetMapping("delete/{codigo}")
	public String deleteStarship(@PathVariable("codigo") Integer codigo, Model model) {
		starshipServiceImpl.delete(codigo);
		model.addAttribute("resultado", "Nave borrada");
		model.addAttribute("option", 2);
		return "ready";
	}
}
