package com.recipes.backend.rest;

import static com.recipes.backend.mapper.IngredientMapper.mapToIngredient;
import static com.recipes.backend.mapper.IngredientMapper.mapToIngredientRest;
import static com.recipes.backend.utils.LogWriter.logHeaders;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.recipes.backend.bizz.ingredient.IngredientService;
import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.security.SecurityService;
import com.recipes.backend.exception.domain.IngredientEmptyException;
import com.recipes.backend.mapper.IngredientMapper;
import com.recipes.backend.rest.domain.IngredientRest;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    private final SecurityService securityService;

    @Autowired
    public IngredientController(
            final IngredientService ingredientService, final SecurityService securityService) {
        this.ingredientService = ingredientService;
        this.securityService = securityService;
    }

    @PostMapping
    public ResponseEntity<Object> addIngredient(
            @RequestHeader HttpHeaders headers, @RequestBody @Valid IngredientRest ingredient) {

        logHeaders(headers);

        if (!securityService.isAuthenticated(headers)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final Ingredient ingredientToAdd =
                mapToIngredient(ingredient).orElseThrow(IngredientEmptyException::new);
        ingredientService.addIngredient(ingredientToAdd);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Set<IngredientRest>> getAllIngredients(
            @RequestHeader HttpHeaders headers,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "limit") int limit) {

        logHeaders(headers);

        if (!securityService.isAuthenticated(headers)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final Set<IngredientRest> retrievedIngredients =
                ingredientService.getAllIngredients(page, limit).stream()
                        .map(IngredientMapper::mapToIngredientRest)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());

        return ResponseEntity.ok(retrievedIngredients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientRest> updateExistingIngredient(
            @RequestHeader HttpHeaders headers, @RequestBody @Valid IngredientRest ingredientRest) {

        logHeaders(headers);

        if (!securityService.isAuthenticated(headers)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var ingredient = mapToIngredient(ingredientRest).orElseThrow(IngredientEmptyException::new);
        var updatedIngredient = ingredientService.updateIngredient(ingredient);
        return mapToIngredientRest(updatedIngredient)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(FORBIDDEN).build());
    }
}
