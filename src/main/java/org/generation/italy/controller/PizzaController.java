package org.generation.italy.controller;

import java.util.List;

import javax.validation.Valid;

import org.generation.italy.model.Pizza;
import org.generation.italy.service.IngredientiService;
import org.generation.italy.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

	@Autowired
	private PizzaService service;
	
	@Autowired
	private IngredientiService ingredientiService;
	
	@GetMapping
	public String menu (Model model, @RequestParam(name="keyword", required=false) String keyword) {
		List<Pizza> result;
		if(keyword != null && !keyword.isEmpty()) {
			result = service.findByKeywordSortedByName(keyword);
			model.addAttribute("keyword", keyword);
		}else {
			result = service.findAllSortedByName();
		}
		model.addAttribute("menu", result);
		return "/pizze/menu";
	}
	
	@GetMapping("/create")
	public String createPizza (Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("pizza", new Pizza() );
		model.addAttribute("ingredienti", ingredientiService.findAllSortByNome());
		return "/pizze/edit";
	}
	
	@PostMapping("/create")
	public String doCreate (@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", false);
			model.addAttribute("ingredienti", ingredientiService.findAllSortByNome());
			return "/pizze/edit";
		}
		service.create(formPizza);
		return "redirect:/pizze";
	}		
	
	@GetMapping("/edit/{id}")
	public String edit (@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("pizza", service.getById(id));
		model.addAttribute("ingredienti", ingredientiService.findAllSortByNome());
		return "/pizze/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model,  RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			model.addAttribute("ingredienti", ingredientiService.findAllSortByNome());
			return "/pizze/edit";
		}
		service.update(formPizza);
		redirectAttributes.addFlashAttribute("successMessage", "Pizza modificata!");
		return "redirect:/pizze";
	}
	
	@GetMapping("/delete/{id}")
	public String doDelete(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		service.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Pizza eliminata!");
		return "redirect:/pizze";
	}
	
}













