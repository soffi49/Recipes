package com.recipes.backend.repo;

import com.recipes.backend.repo.domain.ShopIngredientDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopIngredientRepository extends PagingAndSortingRepository<ShopIngredientDTO, Long> {
}
