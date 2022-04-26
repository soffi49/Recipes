package com.recipes.backend.mapper;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.recipe.domain.RecipeTagEnum;
import com.recipes.backend.repo.domain.TagDTO;
import com.recipes.backend.rest.domain.IngredientRest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TagMapperTest
{
    @Test
    @DisplayName("Map to tagDTO from recipeTagEnum non empty")
    void mapToTagDTONonEmpty()
    {
        final RecipeTagEnum recipeTagEnum = RecipeTagEnum.VEGETARIAN;

        final Optional<TagDTO> result = TagMapper.mapToTagDTO(recipeTagEnum);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("vegetarian");
    }

    @Test
    @DisplayName("Map to tagDTO from recipeTagEnum empty")
    void mapToTagDTOEmpty()
    {
        final Optional<TagDTO> result = TagMapper.mapToTagDTO(null);

        assertThat(result).isEmpty();
    }

}
