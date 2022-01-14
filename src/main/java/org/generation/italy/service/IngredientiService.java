package org.generation.italy.service;

import java.util.List;

import org.generation.italy.model.Ingredienti;
import org.generation.italy.repository.IngredientiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class IngredientiService {

	@Autowired
	private IngredientiRepository repository;
	
	public List<Ingredienti> findByKeywordSortedByNome(String keyword) {
		return repository.findByNomeContainingIgnoreCaseOrderByNome(keyword);
	}
	
	public List<Ingredienti> findAllSortedByNome() {
		return repository.findAll(Sort.by("nome"));
	}
	
	public Ingredienti create(Ingredienti ingredienti) {
		return repository.save(ingredienti);
	}
	
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
	
	public Ingredienti getById(Integer id) {
		return repository.getById(id);
	}
	
}
