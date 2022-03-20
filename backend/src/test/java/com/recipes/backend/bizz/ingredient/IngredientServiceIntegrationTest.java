package com.recipes.backend.bizz.ingredient;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.domain.IngredientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class IngredientServiceIntegrationTest extends AbstractIntegrationTestConfig {

    @Autowired
    private IngredientService ingredientService;

    @MockBean
    private IngredientRepository ingredientRepository;

    private IngredientDTO mockIngredientDTO;

    @BeforeEach
    public void setUp() {
        setUpIngredientMock();
    }


    private void setUpIngredientMock() {
        mockIngredientDTO = new IngredientDTO();
        mockIngredientDTO.setIngredientId(1);
        mockIngredientDTO.setName("Test Name");
    }
}
