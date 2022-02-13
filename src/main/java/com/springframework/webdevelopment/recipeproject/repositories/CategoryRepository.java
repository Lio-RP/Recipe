package com.springframework.webdevelopment.recipeproject.repositories;

import com.springframework.webdevelopment.recipeproject.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
