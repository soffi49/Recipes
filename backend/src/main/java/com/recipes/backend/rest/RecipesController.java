package com.recipes.backend.rest;

import com.recipes.backend.bizz.recipe.RecipeService;
import com.recipes.backend.bizz.security.SecurityService;
import com.recipes.backend.exception.domain.RecipeEmptyException;
import com.recipes.backend.mapper.RecipeMapper;
import com.recipes.backend.rest.domain.FiltersRest;
import com.recipes.backend.rest.domain.RecipeAllRest;
import com.recipes.backend.rest.domain.RecipeRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.recipes.backend.mapper.RecipeMapper.mapToRecipe;
import static com.recipes.backend.utils.LogWriter.logHeaders;

@RestController
@RequestMapping(path = "/recipes")
public class RecipesController
{

    private final RecipeService recipeService;
    private final SecurityService securityService;

    @Autowired
    public RecipesController(final RecipeService recipeService,
                             final SecurityService securityService)
    {
        this.recipeService = recipeService;
        this.securityService = securityService;
    }

    @PostMapping("/all")
    public ResponseEntity<RecipeAllRest> getAllRecipes(@RequestHeader HttpHeaders headers,
                                                       @RequestParam(name = "page") int page,
                                                       @RequestParam(name = "limit") int limit,
                                                       @RequestBody(required = false) FiltersRest filters)
    {
        logHeaders(headers);
        securityService.isAuthenticated(headers);

        final String nameFilter = Objects.nonNull(filters) ? filters.getName() : null;
        final Set<String> tagFilter = Objects.nonNull(filters) ? filters.getTags() : null;
        final Set<RecipeRest> retrievedRecipes =
                recipeService.getAllRecipes(page, limit, nameFilter, tagFilter).stream()
                        .map(RecipeMapper::mapToRecipeRest)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
        final long totalRecipes = recipeService.getRecipesCount();

        return ResponseEntity.ok(new RecipeAllRest(totalRecipes, retrievedRecipes));
    }

    @PostMapping
    public ResponseEntity<Object> addRecipe(@RequestHeader HttpHeaders headers, @RequestBody @Valid RecipeRest recipeRest)
    {
        logHeaders(headers);
        securityService.isAuthenticated(headers);

        final var recipeToAdd = mapToRecipe(recipeRest).orElseThrow(RecipeEmptyException::new);
        recipeService.addRecipe(recipeToAdd);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@RequestHeader HttpHeaders headers,
                                               @PathVariable(name = "id") Long recipeId)
    {
        logHeaders(headers);
        securityService.isAuthenticated(headers);

        return recipeService.deleteRecipe(recipeId) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("Bad request!");
    }

    @PutMapping()
    public ResponseEntity<Object> updateRecipe(@RequestHeader HttpHeaders headers,
                                               @RequestBody RecipeRest recipeRest)
    {
        logHeaders(headers);
        securityService.isAuthenticated(headers);

        recipeService.updateRecipe(mapToRecipe(recipeRest).orElseThrow(RecipeEmptyException::new));
        return ResponseEntity.ok().build();
    }
}
