package com.recipes.backend.rest;

import com.recipes.backend.bizz.ingredient.IngredientService;
import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.security.SecurityService;
import com.recipes.backend.exception.domain.IngredientEmptyException;
import com.recipes.backend.mapper.IngredientMapper;
import com.recipes.backend.rest.domain.FiltersRest;
import com.recipes.backend.rest.domain.IngredientAllRest;
import com.recipes.backend.rest.domain.IngredientRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.recipes.backend.mapper.IngredientMapper.mapToIngredient;
import static com.recipes.backend.utils.LogWriter.logHeaders;

@RestController
@RequestMapping(path = "/ingredients")
public class IngredientController
{

    private final IngredientService ingredientService;
    private final SecurityService securityService;

    @Autowired
    public IngredientController(final IngredientService ingredientService, final SecurityService securityService)
    {
        this.ingredientService = ingredientService;
        this.securityService = securityService;
    }

    @PostMapping
    public ResponseEntity<Object> addIngredient(@RequestHeader HttpHeaders headers,
                                                @RequestBody @Valid IngredientRest ingredient)
    {

        logHeaders(headers);
        securityService.isAuthenticated(headers);

        final Ingredient ingredientToAdd =
                mapToIngredient(ingredient).orElseThrow(IngredientEmptyException::new);
        ingredientService.addIngredient(ingredientToAdd);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/all")
    public ResponseEntity<IngredientAllRest> getAllIngredients(@RequestHeader HttpHeaders headers,
                                                               @RequestParam(value = "page") Integer page,
                                                               @RequestParam(value = "limit") Integer limit,
                                                               @RequestBody(required = false) FiltersRest filters)
    {

        logHeaders(headers);
        securityService.isAuthenticated(headers);

        final String nameFilter = Objects.nonNull(filters) ? filters.getName() : null;
        final List<IngredientRest> retrievedIngredients =
                ingredientService.getAllIngredients(page, limit, nameFilter).stream()
                        .map(IngredientMapper::mapToIngredientRest)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
        final long totalIngredients = ingredientService.getIngredientsCount();

        return ResponseEntity.ok(new IngredientAllRest(totalIngredients, retrievedIngredients));
    }

    // DISCLAIMER: the function below works like the one above. It's a temporary solution (in the future, this endpoint will
    // allow users to get list a list of random ingredients

    @GetMapping("/user")
    public ResponseEntity<IngredientAllRest> getAllIngredientsForUser(@RequestHeader HttpHeaders headers,
                                                                      @RequestParam(value = "page") Integer page,
                                                                      @RequestParam(value = "limit") Integer limit)
    {

        logHeaders(headers);
        securityService.isAuthenticated(headers);

        final List<IngredientRest> retrievedIngredients =
                ingredientService.getAllIngredients(page, limit, null).stream()
                        .map(IngredientMapper::mapToIngredientRest)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
        final long totalIngredients = ingredientService.getIngredientsCount();

        return ResponseEntity.ok(new IngredientAllRest(totalIngredients, retrievedIngredients));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteIngredient(@RequestHeader HttpHeaders headers,
                                                   @PathVariable(name = "id") Long ingredientId)
    {
        logHeaders(headers);
        securityService.isAuthenticated(headers);

        return ingredientService.deleteIngredient(ingredientId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().body("Bad request!");
    }

    @PutMapping()
    public ResponseEntity<IngredientRest> updateExistingIngredient(@RequestHeader HttpHeaders headers,
                                                                   @RequestBody @Valid IngredientRest ingredientRest)
    {

        logHeaders(headers);
        securityService.isAuthenticated(headers);

        var ingredient = mapToIngredient(ingredientRest).orElseThrow(IngredientEmptyException::new);
        ingredientService.updateIngredient(ingredient);
        return ResponseEntity.ok().build();
    }
}
