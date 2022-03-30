package com.recipes.backend.bizz.ingredient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.domain.IngredientDTO;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceUnitTest {

    private static final long ID = 1L;
    private static final String OLD_NAME = "OLD_NAME";
    private static final String NEW_NAME = "NEW_NAME";

    @Mock
    IngredientRepository ingredientRepository;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Captor
    ArgumentCaptor<IngredientDTO> captor;

    @Test
    void shouldCorrectlyUpdateIngredient() {
        // given
        var ingredient = new Ingredient(ID, NEW_NAME);
        var ingredientDTO = new IngredientDTO();
        ingredientDTO.setName(OLD_NAME);
        ingredientDTO.setIngredientId(ID);
        when(ingredientRepository.findById(ID)).thenReturn(Optional.of(ingredientDTO));

        // when
        var result = ingredientService.updateIngredient(ingredient);

        // then
        verify(ingredientRepository, times(1)).findById(ID);
        verify(ingredientRepository, times(1)).save(captor.capture());
        assertThat(result).isEqualTo(ingredient);
        assertThat(captor.getValue().getName()).isEqualTo(NEW_NAME);
    }
}

