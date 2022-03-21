package com.recipes.backend.bizz.ingredient;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.exception.domain.DatabaseSaveException;
import com.recipes.backend.exception.domain.IngredientDuplicateException;
import com.recipes.backend.mapper.IngredientMapper;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.domain.IngredientDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
