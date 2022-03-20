package com.recipes.backend.rest;


import com.recipes.backend.bizz.ingredient.IngredientService;
import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.exception.domain.IngredientEmptyException;
import com.recipes.backend.mapper.IngredientMapper;
import com.recipes.backend.rest.domain.IngredientRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(final IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<Object> addIngredient(@RequestBody @Valid IngredientRest ingredient) {

        final Ingredient ingredientToAdd =
                IngredientMapper.mapToIngredient(ingredient).orElseThrow(IngredientEmptyException::new);
        ingredientService.addIngredient(ingredientToAdd);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<IngredientRest>> getAllIngredients(@RequestParam(value = "page") int page,
                                                                 @RequestParam(value = "limit") int limit) {

        final Set<IngredientRest> retrievedIngredients =
                ingredientService.getAllIngredients(page, limit).stream()
                        .map(IngredientMapper::mapToIngredientRest)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());

        return new ResponseEntity<>(retrievedIngredients, HttpStatus.OK);
    }
}
