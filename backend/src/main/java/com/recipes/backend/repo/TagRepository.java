package com.recipes.backend.repo;

import com.recipes.backend.repo.domain.TagDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends PagingAndSortingRepository<TagDTO, Long> {
}
