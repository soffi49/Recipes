package com.recipes.backend.repo;

import com.recipes.backend.repo.domain.RestaurantRecipeDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRecipeRepository extends PagingAndSortingRepository<RestaurantRecipeDTO, Long> {
}
