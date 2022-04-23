package com.recipes.backend.bizz.ingredient;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.exception.domain.*;
import com.recipes.backend.mapper.IngredientMapper;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.domain.IngredientDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.recipes.backend.mapper.IngredientMapper.mapToIngredient;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService
{

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(final IngredientRepository ingredientRepository)
    {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void addIngredient(final Ingredient ingredient)
    {
        final IngredientDTO ingredientDTO = IngredientMapper.mapToIngredientDTO(ingredient).orElseThrow(IngredientEmptyException::new);

        try
        {
            ingredientRepository.save(ingredientDTO);
        } catch (final DataIntegrityViolationException e)
        {
            throw new IngredientDuplicateException(ingredient.getName());
        } catch (final DataAccessException e)
        {
            throw new DatabaseSaveException("couldn't save ingredient " + ingredient.getName());
        }
    }

    @Override
    public boolean deleteIngredient(Long ingredientId)
    {
        try
        {
            ingredientRepository.deleteById(ingredientId);
            return true;
        } catch (final EmptyResultDataAccessException e)
        {
            throw new IngredientNotFound(ingredientId);
        }
    }

    @Override
    public Set<Ingredient> getAllIngredients(final Integer page,
                                             final Integer limit,
                                             @Nullable final String name) {
        try {
            final Predicate<IngredientDTO> filterIngredientByName = (ingredient -> (Objects.isNull(name) || (ingredient.getName().contains(name))));

            return StreamSupport.stream(ingredientRepository.findAll().spliterator(), false)
                    .filter(filterIngredientByName)
                    .map(IngredientMapper::mapToIngredient)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .skip((long) page * limit)
                    .limit(limit)
                    .collect(Collectors.toSet());
        } catch (final DataAccessException e)
        {
            throw new DatabaseFindException("couldn't retrieve full ingredient list");
        }
    }

    @Override
    public long getIngredientsCount()
    {
        try
        {
            return StreamSupport.stream(ingredientRepository.findAll().spliterator(), false).count();
        } catch (final DataAccessException e)
        {
            throw new DatabaseFindException("couldn't retrieve ingredients count");
        }
    }

    @Override
    public void updateIngredient(final Ingredient ingredient)
    {
        IngredientDTO ingredientDTO;

        try
        {
            ingredientDTO = ingredientRepository.findById(ingredient.getIngredientId()).orElseThrow(() -> new IngredientNotFound(ingredient.getIngredientId()));
        } catch (final DataAccessException e)
        {
            throw new DatabaseFindException(
                    String.format("couldn't retrieve ingredient with id %d to update", ingredient.getIngredientId()));
        }

        try
        {
            ingredientDTO.setName(ingredient.getName());
            ingredientRepository.save(ingredientDTO);

        } catch (final DataAccessException | NoSuchElementException e)
        {
            throw new DatabaseSaveException("couldn't persist updated ingredient");
        }
    }

    @Override
    public boolean isIngredientPresent(final long ingredientId)
    {
        return ingredientRepository.findById(ingredientId).isPresent();
    }
}
