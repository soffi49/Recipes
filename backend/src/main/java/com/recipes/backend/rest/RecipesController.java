package com.recipes.backend.rest;

import com.recipes.backend.bizz.recipe.RecipeService;
import com.recipes.backend.bizz.security.SecurityService;
import com.recipes.backend.exception.domain.RecipeEmptyException;
import com.recipes.backend.mapper.RecipeMapper;
import com.recipes.backend.rest.domain.RecipeAllRest;
import com.recipes.backend.rest.domain.RecipeRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<RecipeAllRest> getAllRecipes(@RequestHeader HttpHeaders headers,
                                                       @RequestParam(name = "page") int page,
                                                       @RequestParam(name = "limit") int limit)
    {
        logHeaders(headers);

        if (!securityService.isAuthenticated(headers))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final Set<RecipeRest> retrievedRecipes =
                recipeService.getAllRecipes(page, limit).stream()
                        .map(RecipeMapper::mapToRecipeRest)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
        final long totalRecipes = recipeService.getRecipesCount();

        return ResponseEntity.ok(new RecipeAllRest(totalRecipes, retrievedRecipes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@RequestHeader HttpHeaders headers,
                                               @PathVariable(name = "id") Long recipeId)
    {
        logHeaders(headers);

        if (!securityService.isAuthenticated(headers))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return recipeService.deleteRecipe(recipeId) ? ResponseEntity.ok(recipeId.toString()) : ResponseEntity.badRequest().body("Bad request!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@RequestHeader HttpHeaders headers,
                                               @PathVariable(name = "id") Long recipeId,
                                               @RequestBody RecipeRest recipeRest)
    {
        logHeaders(headers);

        if (!securityService.isAuthenticated(headers)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        recipeService.updateRecipe(RecipeMapper.mapToRecipe(recipeRest).orElseThrow(RecipeEmptyException::new));
        return ResponseEntity.ok().build();
    }
}
