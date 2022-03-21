package com.recipes.backend.bizz.ingredient;

import static com.recipes.backend.mapper.IngredientMapper.mapToIngredient;
import static com.recipes.backend.mapper.IngredientMapper.mapToIngredientDTO;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.exception.domain.DatabaseFindException;
import com.recipes.backend.exception.domain.DatabaseSaveException;
import com.recipes.backend.exception.domain.IngredientDuplicateException;
import com.recipes.backend.mapper.IngredientMapper;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.domain.IngredientDTO;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
        final Optional<IngredientDTO> ingredientDTOOpt = mapToIngredientDTO(ingredient);

        ingredientDTOOpt.ifPresent(
                ingredientDTO -> {
                    try {
                        ingredientRepository.save(ingredientDTO);
                    } catch (final DataIntegrityViolationException e) {
                        throw new IngredientDuplicateException(ingredient.getName());
                    } catch (final DataAccessException e) {
                        throw new DatabaseSaveException(
                                "couldn't save ingredient " + ingredient.getName());
                    }
                });
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
    public Ingredient updateIngredient(Ingredient ingredient) {
        try {
            var ingredientDTO =
                    ingredientRepository.findById(ingredient.getIngredientId()).orElseThrow();
            ingredientDTO.setName(ingredient.getName());
            ingredientRepository.save(ingredientDTO);
            return mapToIngredient(ingredientDTO).orElseThrow();
        } catch (final DataAccessException | NoSuchElementException e) {
            throw new DatabaseSaveException("couldn't persist updated ingredient");
        }
    }
}
