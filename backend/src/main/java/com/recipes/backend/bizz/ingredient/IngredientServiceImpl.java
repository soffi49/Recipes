package com.recipes.backend.bizz.ingredient;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.exception.domain.DatabaseFindException;
import com.recipes.backend.exception.domain.DatabaseSaveException;
import com.recipes.backend.exception.domain.IngredientDuplicateException;
import com.recipes.backend.mapper.IngredientMapper;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.domain.IngredientDTO;
import com.recipes.backend.rest.domain.IngredientRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(final IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void addIngredient(final Ingredient ingredient) {
        final Optional<IngredientDTO> ingredientDTOOpt = IngredientMapper.mapToIngredientDTO(ingredient);

        ingredientDTOOpt.ifPresent(ingredientDTO -> {
            try {
                ingredientRepository.save(ingredientDTO);
            } catch (final DataIntegrityViolationException e) {
                throw new IngredientDuplicateException(ingredient.getName());
            } catch (final DataAccessException e) {
                throw new DatabaseSaveException("couldn't save ingredient " + ingredient.getName());
            }
        });
    }

    @Override
    public boolean deleteIngredient(Long ingredientId) {
        try {
            ingredientRepository.deleteById(ingredientId);
            return true;
        } catch (final EmptyResultDataAccessException e) {
            log.warn("Ingredient id {} deletion failed!", ingredientId, e);
            return false;
        }
    }

    @Override
    public Set<Ingredient> getAllIngredients(final Integer page, final Integer limit) {
        try {
            return StreamSupport.stream(ingredientRepository.findAll().spliterator(), false)
                    .map(IngredientMapper::mapToIngredient)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .skip((long) page * limit)
                    .limit(limit)
                    .collect(Collectors.toSet());
        } catch (final DataAccessException e) {
            throw new DatabaseFindException("couldn't persist full ingredient list");
        }
    }

    @Override
    public long getIngredientsCount() {
        try {
            return StreamSupport.stream(ingredientRepository.findAll().spliterator(), false).count();
        } catch (final DataAccessException e) {
            throw new DatabaseFindException("couldn't persist ingredients count");
        }
    }
}
