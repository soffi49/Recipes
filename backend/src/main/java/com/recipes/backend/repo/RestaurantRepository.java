package com.recipes.backend.repo;

import com.recipes.backend.repo.domain.RestaurantDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<RestaurantDTO, Long> {
}
