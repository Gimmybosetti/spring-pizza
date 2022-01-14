package org.generation.italy.repository;

import java.util.List;

import org.generation.italy.model.Ingredienti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientiRepository extends JpaRepository<Ingredienti, Integer> {

	List<Ingredienti> findByNomeContainingIgnoreCaseOrderByNome(String keyword);
	
}
