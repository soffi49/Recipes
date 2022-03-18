package com.recipes.backend.repo;

import com.recipes.backend.repo.domain.IngredientDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends PagingAndSortingRepository<IngredientDTO, Long> {

}
