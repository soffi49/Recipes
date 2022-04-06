package com.recipes.backend.repo;

import com.recipes.backend.repo.domain.RecipeDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<RecipeDTO, Long>
{
}
