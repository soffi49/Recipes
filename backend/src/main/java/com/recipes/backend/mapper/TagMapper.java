package com.recipes.backend.mapper;

import com.recipes.backend.bizz.recipe.domain.RecipeTagEnum;
import com.recipes.backend.repo.domain.TagDTO;
import java.util.Objects;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TagMapper {

    public static Optional<TagDTO> mapToTagDTO(final RecipeTagEnum recipeTag) {
        if (Objects.nonNull(recipeTag)) {
            final var tagDTO = new TagDTO();
            tagDTO.setTagId(recipeTag.getId());
            tagDTO.setName(recipeTag.getName());
            return Optional.of(tagDTO);
        }

        return Optional.empty();
    }
}
