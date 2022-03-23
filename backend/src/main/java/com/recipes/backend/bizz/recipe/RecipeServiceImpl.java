package com.recipes.backend.bizz.recipe;

import com.recipes.backend.repo.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(final RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public boolean deleteRecipe(Long recipeId) {
        try {
            recipeRepository.deleteById(recipeId);
            return true;
        } catch (final EmptyResultDataAccessException e) {
            log.warn("Recipe id {} deletion failed!", recipeId, e);
            return false;
        }
    }
}
