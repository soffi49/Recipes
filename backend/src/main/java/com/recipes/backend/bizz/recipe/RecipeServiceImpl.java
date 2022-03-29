package com.recipes.backend.bizz.recipe;

import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.exception.domain.DatabaseFindException;
import com.recipes.backend.mapper.RecipeMapper;
import com.recipes.backend.repo.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(final RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public long getRecipesCount() {
        try {
            return StreamSupport.stream(recipeRepository.findAll().spliterator(), false).count();
        } catch (final DataAccessException e) {
            throw new DatabaseFindException("couldn't persist recipes count");
        }
    }

    @Override
    public Set<Recipe> getAllRecipes(final Integer page, final Integer limit) {
        try {
            return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                    .map(RecipeMapper::mapToRecipe)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .skip((long) page * limit)
                    .limit(limit)
                    .collect(Collectors.toSet());
        } catch (final DataAccessException e) {
            throw new DatabaseFindException("couldn't persist full recipe list");
        }
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
