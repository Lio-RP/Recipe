package com.springframework.webdevelopment.recipeproject.repositories;

import com.springframework.webdevelopment.recipeproject.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
