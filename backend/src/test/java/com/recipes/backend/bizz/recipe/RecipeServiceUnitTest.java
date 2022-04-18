package com.recipes.backend.bizz.recipe;

import com.recipes.backend.bizz.ingredient.IngredientService;
import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.bizz.recipe.domain.RecipeTagEnum;
import com.recipes.backend.exception.domain.*;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.RecipeIngredientRepository;
import com.recipes.backend.repo.RecipeRepository;
import com.recipes.backend.repo.TagRepository;
import com.recipes.backend.repo.domain.IngredientDTO;
import com.recipes.backend.repo.domain.RecipeDTO;
import com.recipes.backend.repo.domain.RecipeIngredientDTO;
import com.recipes.backend.repo.domain.TagDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
class RecipeServiceUnitTest
{

    private static final long ID = 1000L;
    private static List<RecipeDTO> mockRecipeSet;
    private static Recipe mockRecipe;
    private static TagDTO mockTag;
    private static IngredientDTO mockIngredient;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    TagRepository tagRepository;
    @Mock
    IngredientRepository ingredientRepository;
    @Mock
    RecipeIngredientRepository recipeIngredientRepository;
    @Mock
    IngredientService ingredientService;

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Captor
    ArgumentCaptor<RecipeDTO> captor;

    @BeforeAll
    static void setUp()
    {
        final Ingredient mockIngredient = new Ingredient();
        mockIngredient.setQuantity("10g");
        mockIngredient.setName("Ingredient");
        mockIngredient.setIngredientId(1000L);

        mockRecipe = new Recipe();
        mockRecipe.setRecipeId(1000L);
        mockRecipe.setName("Name1");
        mockRecipe.setInstructions("Instructions");
        mockRecipe.setTags(Set.of(RecipeTagEnum.VEGETARIAN));
        mockRecipe.setIngredients(Set.of(mockIngredient));

        setUpRecipeList();
    }

    @Test
    @DisplayName("Add recipe with correct data")
    void addRecipeCorrectData()
    {
        recipeService.addRecipe(mockRecipe);

        verify(recipeRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Name1");
    }

    @Test
    @DisplayName("Add recipe with incorrect data")
    void addRecipeIncorrectData()
    {
        assertThatThrownBy(() -> recipeService.addRecipe(null))
                .isExactlyInstanceOf(RecipeEmptyException.class)
                .hasMessage("The given recipe have some fields which are empty");
    }

    @Test
    @DisplayName("Add recipe which is duplicated")
    void addRecipeDuplicate()
    {
        lenient().when(recipeRepository.save(any(RecipeDTO.class))).thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> recipeService.addRecipe(mockRecipe))
                .isExactlyInstanceOf(RecipeDuplicateException.class)
                .hasMessage("Recipe with name: Name1 does exist in database");
    }

    @Test
    @DisplayName("Add recipe internal database error")
    void addRecipeDatabaseException()
    {
        lenient().when(recipeRepository.save(any(RecipeDTO.class))).thenThrow((mock(DataAccessException.class)));

        assertThatThrownBy(() -> recipeService.addRecipe(mockRecipe))
                .isExactlyInstanceOf(DatabaseSaveException.class)
                .hasMessage("Internal database error while saving: couldn't save recipe Name1");
    }

    @Test
    @DisplayName("Delete existing recipe")
    void deleteExistingRecipe()
    {
        final boolean deleteResult = recipeService.deleteRecipe(ID);

        verify(recipeRepository, times(1)).deleteById(ID);
        assertThat(deleteResult).isTrue();
    }

    @Test
    @DisplayName("Delete non existing recipe")
    void deleteNonExistingRecipe()
    {
        doThrow(EmptyResultDataAccessException.class).when(recipeRepository).deleteById(ID);

        assertThatThrownBy(() -> recipeService.deleteRecipe(ID))
                .isExactlyInstanceOf(RecipeNotFound.class)
                .hasMessage("Recipe with the id: 1000 does not exist");
    }

    @Test
    @DisplayName("Get all recipe one page")
    void getAllRecipesOnePage()
    {
        final int limit = 3;
        final int page = 0;
        when(recipeRepository.findAll()).thenReturn(mockRecipeSet);

        final Set<Recipe> retrievedList = recipeService.getAllRecipes(page, limit);
        final Set<String> ingredientsNames = retrievedList.stream().map(Recipe::getName).collect(Collectors.toSet());

        assertThat(retrievedList).hasSize(3);
        assertThat(ingredientsNames).contains("Name3");
    }

    @Test
    @DisplayName("Get all recipes more pages")
    void getAllRecipesMorePages()
    {
        final int limit = 2;
        when(recipeRepository.findAll()).thenReturn(mockRecipeSet);

        final Set<Recipe> retrievedList1 = recipeService.getAllRecipes(0, limit);
        final Set<Recipe> retrievedList2 = recipeService.getAllRecipes(1, limit);

        assertThat(retrievedList1).hasSize(2);
        assertThat(retrievedList2).hasSize(1);
        assertThat(retrievedList1.stream().anyMatch(el -> el.getName().equals("Name3"))).isFalse();
    }

    @Test
    @DisplayName("Get all recipes empty")
    void getAllRecipesEmpty()
    {
        final int limit = 5;
        final int page = 0;
        when(recipeRepository.findAll()).thenReturn(Collections.emptySet());

        final Set<Recipe> retrievedList = recipeService.getAllRecipes(page, limit);

        assertThat(retrievedList).isEmpty();
    }

    @Test
    @DisplayName("Get all recipes internal database error")
    void getAllRecipesDatabaseError()
    {
        final int limit = 3;
        final int page = 0;
        lenient().when(recipeRepository.findAll()).thenThrow((mock(DataAccessException.class)));

        assertThatThrownBy(() -> recipeService.getAllRecipes(page, limit))
                .isExactlyInstanceOf(DatabaseFindException.class)
                .hasMessage("Internal database error while reading data: couldn't retrieve full recipe list");
    }

    @Test
    @DisplayName("Count recipes internal database error")
    void countRecipesDatabaseError()
    {
        lenient().when(recipeRepository.findAll()).thenThrow((mock(DataAccessException.class)));

        assertThatThrownBy(() -> recipeService.getRecipesCount())
                .isExactlyInstanceOf(DatabaseFindException.class)
                .hasMessage("Internal database error while reading data: couldn't retrieve recipes count");

    }

    @Test
    @DisplayName("Count recipes not empty")
    void countRecipesNotEmpty()
    {
        when(recipeRepository.findAll()).thenReturn(mockRecipeSet);

        final long retrievedCount = recipeService.getRecipesCount();

        assertThat(retrievedCount).isEqualTo(3);
    }

    @Test
    @DisplayName("Count recipes empty")
    void countRecipesEmpty()
    {
        when(recipeRepository.findAll()).thenReturn(Collections.emptySet());

        final long retrievedCount = recipeService.getRecipesCount();

        assertThat(retrievedCount).isZero();
    }

    @Test
    @DisplayName("Update recipe with correct data")
    void updateRecipeCorrectData()
    {
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(mockRecipeSet.get(0)));
        lenient().when(tagRepository.findById(any())).thenReturn(Optional.of(mockTag));
        lenient().when(ingredientRepository.findById(any())).thenReturn(Optional.of(mockIngredient));
        lenient().when(ingredientService.isIngredientPresent(any(Long.TYPE))).thenReturn(true);

        recipeService.updateRecipe(mockRecipe);

        verify(recipeRepository, times(1)).findById(ID);
        verify(recipeRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Name1");
    }

    @Test
    @DisplayName("Update recipe which is not found")
    void updateNotFoundRecipe()
    {
        when(recipeRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recipeService.updateRecipe(mockRecipe))
                .isExactlyInstanceOf(RecipeNotFound.class)
                .hasMessage("Recipe with the id: 1000 does not exist");

    }

    @Test
    @DisplayName("Update recipe with tag not found")
    void updateRecipeTagNotFound()
    {
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(mockRecipeSet.get(0)));
        lenient().when(tagRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recipeService.updateRecipe(mockRecipe))
                .isExactlyInstanceOf(TagNotFound.class)
                .hasMessage("Tag with the id: 1 does not exist");

    }

    @Test
    @DisplayName("Update recipe internal database error on save")
    void updateRecipeDatabaseErrorOnSave()
    {
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(mockRecipeSet.get(0)));
        lenient().when(tagRepository.findById(any())).thenReturn(Optional.of(mockTag));
        lenient().when(recipeRepository.save(any(RecipeDTO.class))).thenThrow((mock(DataAccessException.class)));

        assertThatThrownBy(() -> recipeService.updateRecipe(mockRecipe))
                .isExactlyInstanceOf(DatabaseSaveException.class)
                .hasMessage("Internal database error while saving: couldn't save the updated recipe");

    }

    @Test
    @DisplayName("Update recipe with ingredient not found")
    void updateRecipeIngredientNotFound()
    {
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(mockRecipeSet.get(0)));
        lenient().when(tagRepository.findById(any())).thenReturn(Optional.of(mockTag));
        lenient().when(ingredientRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recipeService.updateRecipe(mockRecipe))
                .isExactlyInstanceOf(IngredientNotFound.class)
                .hasMessage("Ingredient with the id: 1000 does not exist");

    }

    @Test
    @DisplayName("Update recipe ingredient list - database internal error")
    void updateRecipeDatabaseErrorOnIngredientListSave()
    {
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(mockRecipeSet.get(0)));
        lenient().when(tagRepository.findById(any())).thenReturn(Optional.of(mockTag));
        lenient().when(ingredientRepository.findById(any())).thenReturn(Optional.of(mockIngredient));
        lenient().when(ingredientService.isIngredientPresent(any(Long.TYPE))).thenReturn(true);
        lenient().doThrow(mock(DataAccessException.class)).when(recipeIngredientRepository).addIngredientForRecipe(any(Long.TYPE), any(Long.TYPE), anyString());

        assertThatThrownBy(() -> recipeService.updateRecipe(mockRecipe))
                .isExactlyInstanceOf(DatabaseSaveException.class)
                .hasMessage("Internal database error while saving: couldn't update the ingredient list for recipe");

    }

    private static void setUpRecipeList()
    {
        mockTag = new TagDTO();
        mockTag.setName("vegetarian");
        mockTag.setTagId(1L);

        mockIngredient = new IngredientDTO();
        mockIngredient.setName("Ingredient");
        mockIngredient.setIngredientId(1000L);

        final RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
        recipeIngredientDTO.setIngredient(mockIngredient);
        recipeIngredientDTO.setQuantity("10g");

        mockRecipeSet = new ArrayList<>();

        IntStream.rangeClosed(1, 3).forEach(idx -> {
            final RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.setRecipeId(idx * 1000L);
            recipeDTO.setName("Name" + idx);
            recipeDTO.setInstructions("Instructions");
            recipeDTO.setIngredientSet(Set.of(recipeIngredientDTO));
            recipeDTO.setTagSet(Set.of(mockTag));

            mockRecipeSet.add(recipeDTO);
        });
    }
}
