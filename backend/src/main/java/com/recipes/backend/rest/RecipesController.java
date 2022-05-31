package com.recipes.backend.rest;

import static com.recipes.backend.mapper.RecipeMapper.mapToRecipe;
import static com.recipes.backend.utils.LogWriter.logHeaders;

import com.recipes.backend.bizz.recipe.RecipeService;
import com.recipes.backend.bizz.security.SecurityService;
import com.recipes.backend.exception.domain.IngredientEmptyException;
import com.recipes.backend.exception.domain.RecipeEmptyException;
import com.recipes.backend.mapper.IngredientMapper;
import com.recipes.backend.mapper.RecipeMapper;
import com.recipes.backend.rest.domain.FiltersRest;
import com.recipes.backend.rest.domain.IngredientRest;
import com.recipes.backend.rest.domain.RecipeAllRest;
import com.recipes.backend.rest.domain.RecipeRest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        final List<RecipeRest> retrievedRecipes =
                recipeService.getAllRecipes(page, limit, nameFilter, tagFilter).stream()
                        .map(RecipeMapper::mapToRecipeRest)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
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

    @PostMapping("/user")
    public ResponseEntity<Object> getRecipes(@RequestHeader HttpHeaders headers,
        @RequestBody List<IngredientRest> ingredientRests) {
        logHeaders(headers);
        securityService.isAuthenticated(headers);

        var ingredients = ingredientRests.stream()
            .map(i -> IngredientMapper.mapToIngredient(i).orElseThrow(IngredientEmptyException::new))
            .collect(Collectors.toList());

        var foundRecipes = recipeService.findRecipes(ingredients).stream()
            .map(r -> RecipeMapper.mapToRecipeRest(r).orElseThrow(RecipeEmptyException::new))
            .collect(Collectors.toList());

        return ResponseEntity.ok(new RecipeAllRest(foundRecipes.size(), foundRecipes));
    }
}
