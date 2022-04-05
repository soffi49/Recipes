package com.recipes.backend.bizz.recipe;

import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.exception.domain.*;
import com.recipes.backend.mapper.RecipeMapper;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.RecipeIngredientRepository;
import com.recipes.backend.repo.RecipeRepository;
import com.recipes.backend.repo.TagRepository;
import com.recipes.backend.repo.domain.RecipeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService
{

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final TagRepository tagRepository;

    @Autowired
    public RecipeServiceImpl(final RecipeRepository recipeRepository,
                             final IngredientRepository ingredientRepository,
                             final RecipeIngredientRepository recipeIngredientRepository,
                             final TagRepository tagRepository)
    {
        this.tagRepository = tagRepository;
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public long getRecipesCount()
    {
        try
        {
            return StreamSupport.stream(recipeRepository.findAll().spliterator(), false).count();
        } catch (final DataAccessException e)
        {
            throw new DatabaseFindException("couldn't persist recipes count");
        }
    }

    @Override
    public Set<Recipe> getAllRecipes(final Integer page, final Integer limit)
    {
        try
        {
            return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                    .map(RecipeMapper::mapToRecipe)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .skip((long) page * limit)
                    .limit(limit)
                    .collect(Collectors.toSet());
        } catch (final DataAccessException e)
        {
            throw new DatabaseFindException("couldn't persist full recipe list");
        }
    }

    @Override
    public boolean deleteRecipe(final Long recipeId)
    {
        try
        {
            recipeRepository.deleteById(recipeId);
            return true;
        } catch (final EmptyResultDataAccessException e)
        {
            log.warn("Recipe id {} deletion failed!", recipeId, e);
            return false;
        }
    }

    @Transactional
    @Override
    public void updateRecipe(final Recipe recipe)
    {
        final RecipeDTO recipeToBeEdited =
                recipeRepository.findById(recipe.getRecipeId()).orElseThrow(() -> new RecipeNotFound(recipe.getRecipeId()));

        recipeToBeEdited.setName(recipe.getName());
        recipeToBeEdited.setInstructions(recipe.getInstructions());
        recipeToBeEdited.setTagSet(recipe.getTags().stream()
                                           .map(tag -> tagRepository.findById(tag.getId()).orElseThrow(() -> new TagNotFound(tag.getId())))
                                           .collect(Collectors.toSet()));

        try
        {
            recipeRepository.save(recipeToBeEdited);
        } catch (final DataAccessException e)
        {
            throw new DatabaseSaveException("couldn't save the updated recipe");
        }

        try
        {
            recipeIngredientRepository.deleteAllByRecipeId(recipeToBeEdited.getRecipeId());
            recipe.getIngredients().forEach(ingredient -> {
                if (ingredientRepository.findById(ingredient.getIngredientId()).isEmpty())
                {
                    throw new IngredientNotFound(ingredient.getIngredientId());
                }
                recipeIngredientRepository.addIngredientForRecipe(recipe.getRecipeId(), ingredient.getIngredientId(), ingredient.getQuantity());
            });
        } catch (final DataAccessException e)
        {
            throw new DatabaseSaveException("couldn't update the ingredient list for recipe");
        }
    }
}
