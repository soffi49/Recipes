package com.recipes.backend.repo;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.repo.domain.TagDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TagRepositoryTest extends AbstractIntegrationTestConfig
{
    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Find tag by id non empty")
    @Order(1)
    void findByIdNonEmpty()
    {
        final Optional<TagDTO> result = tagRepository.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("vegetarian");
    }

    @Test
    @DisplayName("Find tag by id empty")
    @Order(2)
    void findByIdEmpty()
    {
        final Optional<TagDTO> result = tagRepository.findById(10L);

        assertThat(result).isEmpty();
    }
}
