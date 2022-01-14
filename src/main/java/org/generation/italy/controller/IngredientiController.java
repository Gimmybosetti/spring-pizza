package org.generation.italy.controller;

import java.util.List;

import javax.validation.Valid;

import org.generation.italy.model.Ingredienti;
import org.generation.italy.service.IngredientiService;
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

@RequestMapping("/ingredienti")
@Controller
public class IngredientiController {
	
	@Autowired
	private IngredientiService service;
	
	@GetMapping
	public String list (Model model, @RequestParam(name="keyword", required=false) String keyword) {
		List<Ingredienti> result;
		if(keyword != null && !keyword.isEmpty()) {
			result = service.findByKeywordSortedByNome(keyword);
			model.addAttribute("keyword", keyword);
		}else {
			result = service.findAllSortedByNome();
		}
		model.addAttribute("list", result);
		return "/ingredienti/list";
	}
	
	@GetMapping("/create")
	public String create (Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("ingrediente", new Ingredienti());
		return "/ingredienti/edit";
	}
	
	@PostMapping("/create")
	public String doCreate (@Valid @ModelAttribute("ingrediente") Ingredienti ingrediente, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", false);
			return "/ingredienti/edit";
		}
		Ingredienti insertIngrediente = service.create(ingrediente);
		redirectAttributes.addFlashAttribute("successMessage", "Ingrediente " + insertIngrediente.getId() + " creato");
		return "redirect:/ingredienti";
	}
	
	@GetMapping("/delete/{id}")
	public String doDelete(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		service.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Ingrediente cancellato");
		return "redirect:/ingredienti";
	}
	
	@GetMapping("/edit/{id}")
	public String edit (@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("ingrediente", service.getById(id));
		return "/ingredienti/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String doEdit (@Valid @ModelAttribute("ingrediente") Ingredienti ingrediente, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			return "/ingredienti/edit";
		}
		service.create(ingrediente);
		redirectAttributes.addFlashAttribute("successMessage", "Ingrediente aggiornato");
		return "redirect:/ingredienti";
	}
	
}

















