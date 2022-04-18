package com.recipes.backend.bizz.ingredient;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.exception.domain.*;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.domain.IngredientDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceUnitTest
{
    private static final long ID = 1L;
    private static final String OLD_NAME = "OLD_NAME";
    private static final String NEW_NAME = "NEW_NAME";
    private static final String QUANTITY = "QUANTITY";
    private static List<IngredientDTO> mockIngredientSet;

    @Mock
    IngredientRepository ingredientRepository;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Captor
    ArgumentCaptor<IngredientDTO> captor;


    @BeforeAll
    static void setUp()
    {
        mockIngredientSet = new ArrayList<>();

        IntStream.rangeClosed(1, 3).forEach(idx -> {
            final IngredientDTO ingredientDTO = new IngredientDTO();
            ingredientDTO.setIngredientId(idx);
            ingredientDTO.setName("Name" + idx);
            mockIngredientSet.add(ingredientDTO);
        });
    }

    @Test
    @DisplayName("Add ingredient with correct data")
    void addIngredientCorrectData()
    {
        final Ingredient ingredient = new Ingredient(ID, NEW_NAME, QUANTITY);
        final IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setName(OLD_NAME);
        ingredientDTO.setIngredientId(ID);

        ingredientService.addIngredient(ingredient);

        verify(ingredientRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo(NEW_NAME);
    }

    @Test
    @DisplayName("Add ingredient with incorrect data")
    void addIngredientIncorrectData()
    {
        assertThatThrownBy(() -> ingredientService.addIngredient(null))
                .isExactlyInstanceOf(IngredientEmptyException.class)
                .hasMessage("The given ingredient have some fields which are empty");
    }

    @Test
    @DisplayName("Add ingredient which is duplicated")
    void addIngredientDuplicate()
    {
        final Ingredient ingredient = new Ingredient(ID, NEW_NAME, QUANTITY);
        lenient().when(ingredientRepository.save(any(IngredientDTO.class))).thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> ingredientService.addIngredient(ingredient))
                .isExactlyInstanceOf(IngredientDuplicateException.class)
                .hasMessage("Ingredient with name: NEW_NAME does exist in database");
    }

    @Test
    @DisplayName("Add ingredient internal database error")
    void addIngredientDatabaseException()
    {
        final Ingredient ingredient = new Ingredient(ID, NEW_NAME, QUANTITY);
        lenient().when(ingredientRepository.save(any(IngredientDTO.class))).thenThrow((mock(DataAccessException.class)));

        assertThatThrownBy(() -> ingredientService.addIngredient(ingredient))
                .isExactlyInstanceOf(DatabaseSaveException.class)
                .hasMessage("Internal database error while saving: couldn't save ingredient NEW_NAME");
    }

    @Test
    @DisplayName("Delete existing ingredient")
    void deleteExistingIngredient()
    {
        final boolean deleteResult = ingredientService.deleteIngredient(ID);

        verify(ingredientRepository, times(1)).deleteById(ID);
        assertThat(deleteResult).isTrue();
    }

    @Test
    @DisplayName("Delete non existing ingredient")
    void deleteNonExistingIngredient()
    {
        doThrow(EmptyResultDataAccessException.class).when(ingredientRepository).deleteById(ID);

        assertThatThrownBy(() -> ingredientService.deleteIngredient(ID))
                .isExactlyInstanceOf(IngredientNotFound.class)
                .hasMessage("Ingredient with the id: 1 does not exist");
    }

    @Test
    @DisplayName("Get all ingredients one page without filters")
    void getAllIngredientsOnePageWithoutFilters()
    {
        final int limit = 3;
        final int page = 0;
        when(ingredientRepository.findAll()).thenReturn(mockIngredientSet);

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, null);
        final Set<String> ingredientsNames = retrievedList.stream().map(Ingredient::getName).collect(Collectors.toSet());

        assertThat(retrievedList).hasSize(3);
        assertThat(ingredientsNames).contains("Name3");
    }

    @Test
    @DisplayName("Get all ingredients more pages without filters")
    void getAllIngredientsMorePagesWithoutFilters()
    {
        final int limit = 2;
        when(ingredientRepository.findAll()).thenReturn(mockIngredientSet);

        final Set<Ingredient> retrievedList1 = ingredientService.getAllIngredients(0, limit, null);
        final Set<Ingredient> retrievedList2 = ingredientService.getAllIngredients(1, limit, null);

        assertThat(retrievedList1).hasSize(2);
        assertThat(retrievedList2).hasSize(1);
        assertThat(retrievedList1.stream().anyMatch(el -> el.getName().equals("Name3"))).isFalse();
    }

    @Test
    @DisplayName("Get all ingredients with existing name")
    void getAllIngredientsWithSpecifiedName()
    {
        final int limit = 3;
        final int page = 0;
        when(ingredientRepository.findAll()).thenReturn(mockIngredientSet);

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, "Name1");

        assertThat(retrievedList.size()).isOne();
    }

    @Test
    @DisplayName("Get all ingredients with not existing name")
    void getAllNotExistingIngredientsWithSpecifiedName()
    {
        final int limit = 3;
        final int page = 0;
        when(ingredientRepository.findAll()).thenReturn(mockIngredientSet);

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, "Name10");

        assertThat(retrievedList).isEmpty();
    }

    @Test
    @DisplayName("Get all ingredients empty without filters")
    void getAllIngredientsEmptyWithoutFilters()
    {
        final int limit = 5;
        final int page = 0;
        when(ingredientRepository.findAll()).thenReturn(Collections.emptySet());

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, null);

        assertThat(retrievedList).isEmpty();
    }

    @Test
    @DisplayName("Get all ingredients internal database error")
    void getAllIngredientsDatabaseError()
    {
        final int limit = 3;
        final int page = 0;
        lenient().when(ingredientRepository.findAll()).thenThrow((mock(DataAccessException.class)));

        assertThatThrownBy(() -> ingredientService.getAllIngredients(page, limit, null))
                .isExactlyInstanceOf(DatabaseFindException.class)
                .hasMessage("Internal database error while reading data: couldn't retrieve full ingredient list");
    }

    @Test
    @DisplayName("Count ingredients internal database error")
    void countIngredientsDatabaseError()
    {
        lenient().when(ingredientRepository.findAll()).thenThrow((mock(DataAccessException.class)));

        assertThatThrownBy(() -> ingredientService.getIngredientsCount())
                .isExactlyInstanceOf(DatabaseFindException.class)
                .hasMessage("Internal database error while reading data: couldn't retrieve ingredients count");

    }

    @Test
    @DisplayName("Count ingredients not empty")
    void countIngredientsNotEmpty()
    {
        when(ingredientRepository.findAll()).thenReturn(mockIngredientSet);

        final long retrievedList = ingredientService.getIngredientsCount();

        assertThat(retrievedList).isEqualTo(3);
    }

    @Test
    @DisplayName("Count ingredients empty")
    void countIngredientsEmpty()
    {
        when(ingredientRepository.findAll()).thenReturn(Collections.emptySet());

        final long retrievedList = ingredientService.getIngredientsCount();

        assertThat(retrievedList).isZero();
    }

    @Test
    @DisplayName("Is ingredient present true")
    void isIngredientPresentTrue()
    {
        final IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setName(OLD_NAME);
        ingredientDTO.setIngredientId(ID);

        when(ingredientRepository.findById(ID)).thenReturn(Optional.of(ingredientDTO));

        final boolean result = ingredientService.isIngredientPresent(ID);

        verify(ingredientRepository, times(1)).findById(ID);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Is ingredient present false")
    void isIngredientPresentFalse()
    {
        when(ingredientRepository.findById(ID)).thenReturn(Optional.empty());

        final boolean result = ingredientService.isIngredientPresent(ID);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Update ingredient with correct data")
    void updateIngredientCorrectData()
    {
        var ingredient = new Ingredient(ID, NEW_NAME, QUANTITY);
        var ingredientDTO = new IngredientDTO();
        ingredientDTO.setName(OLD_NAME);
        ingredientDTO.setIngredientId(ID);
        when(ingredientRepository.findById(ID)).thenReturn(Optional.of(ingredientDTO));

        var result = ingredientService.updateIngredient(ingredient);

        verify(ingredientRepository, times(1)).findById(ID);
        verify(ingredientRepository, times(1)).save(captor.capture());
        assertThat(result).isEqualTo(ingredient);
        assertThat(captor.getValue().getName()).isEqualTo(NEW_NAME);
    }

    @Test
    @DisplayName("Update ingredient which is not found")
    void updateNotFoundIngredient()
    {
        final Ingredient ingredient = new Ingredient(ID, NEW_NAME, QUANTITY);
        when(ingredientRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ingredientService.updateIngredient(ingredient))
                .isExactlyInstanceOf(IngredientNotFound.class)
                .hasMessage("Ingredient with the id: 1 does not exist");

    }

    @Test
    @DisplayName("Update ingredient internal database error on find")
    void updateIngredientDatabaseErrorOnFind()
    {
        final Ingredient ingredient = new Ingredient(ID, NEW_NAME, QUANTITY);
        lenient().when(ingredientRepository.findById(ID)).thenThrow((mock(DataAccessException.class)));

        assertThatThrownBy(() -> ingredientService.updateIngredient(ingredient))
                .isExactlyInstanceOf(DatabaseFindException.class)
                .hasMessage("Internal database error while reading data: couldn't retrieve ingredient with id 1 to update");

    }

    @Test
    @DisplayName("Update ingredient internal database error on save")
    void updateIngredientDatabaseErrorOnSave()
    {
        final Ingredient ingredient = new Ingredient(ID, NEW_NAME, QUANTITY);
        final IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setName(OLD_NAME);
        ingredientDTO.setIngredientId(ID);
        when(ingredientRepository.findById(ID)).thenReturn(Optional.of(ingredientDTO));
        lenient().when(ingredientRepository.save(ingredientDTO)).thenThrow((mock(DataAccessException.class)));

        assertThatThrownBy(() -> ingredientService.updateIngredient(ingredient))
                .isExactlyInstanceOf(DatabaseSaveException.class)
                .hasMessage("Internal database error while saving: couldn't persist updated ingredient");

    }

}

