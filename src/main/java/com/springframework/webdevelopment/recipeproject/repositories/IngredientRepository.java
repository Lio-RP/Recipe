package com.springframework.webdevelopment.recipeproject.repositories;

import com.springframework.webdevelopment.recipeproject.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
