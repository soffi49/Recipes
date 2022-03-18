package com.recipes.backend.rest;

import com.recipes.backend.repo.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/recipe")
public class RecipesController {

    private final IngredientRepository exampleRepo;

    @Autowired
    public RecipesController(final IngredientRepository exampleRepo) {
        this.exampleRepo = exampleRepo;
    }

    @PostMapping("{recipeId}")
    public void addRecipe(@RequestHeader HttpHeaders headers, @PathVariable Long recipeId) {
        exampleRepo.deleteAll();
    }
}
