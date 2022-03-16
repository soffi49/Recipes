package com.recipes.backend.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepo extends PagingAndSortingRepository<Long, Long> {

}
