package com.recipes.backend.rest;

import com.recipes.backend.bizz.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/recipes")
public class RecipesController {

    private final RecipeService recipeService;

    @Autowired
    public RecipesController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@RequestHeader HttpHeaders headers,
                                               @PathVariable(name = "id") Long recipeId) {
        //TODO add header validation
        if (recipeService.deleteRecipe(recipeId)) {
            return ResponseEntity.ok(recipeId.toString());
        }
        return ResponseEntity.badRequest().body("Bad request!");
    }
}
