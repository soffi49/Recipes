package com.recipes.backend.rest;
import com.recipes.backend.bizz.ingredient.IngredientService;
import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.exception.domain.IngredientEmptyException;
import com.recipes.backend.mapper.IngredientMapper;
import com.recipes.backend.rest.domain.IngredientRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<Object> addIngredient(@RequestHeader HttpHeaders headers,
                                                @RequestBody @Valid IngredientRest ingredient) {

        //TODO add header validation

        final Ingredient ingredientToAdd =
                IngredientMapper.mapToIngredient(ingredient).orElseThrow(IngredientEmptyException::new);
        ingredientService.addIngredient(ingredientToAdd);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Set<IngredientRest>> getAllIngredients(@RequestHeader HttpHeaders headers,
                                                                 @RequestParam(value = "page") int page,
                                                                 @RequestParam(value = "limit") int limit) {

        //TODO check headers

        final Set<IngredientRest> retrievedIngredients =
                ingredientService.getAllIngredients(page, limit).stream()
                        .map(IngredientMapper::mapToIngredientRest)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());

        return ResponseEntity.ok(retrievedIngredients);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIngredient(@RequestHeader HttpHeaders headers,
                                                   @PathVariable(name = "id") Long ingredientId) {
        //TODO add header validation
        return ingredientService.deleteIngredient(ingredientId) ? ResponseEntity.ok(ingredientId.toString()) : ResponseEntity.badRequest().body("Bad request!");
    }
}
