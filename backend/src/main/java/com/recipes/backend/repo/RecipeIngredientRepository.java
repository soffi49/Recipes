package com.recipes.backend.repo;

import com.recipes.backend.repo.domain.RecipeIngredientDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends PagingAndSortingRepository<RecipeIngredientDTO, Long> {
}
