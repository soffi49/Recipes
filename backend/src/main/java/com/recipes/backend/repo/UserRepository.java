package com.recipes.backend.repo;

import com.recipes.backend.repo.domain.UserDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserDTO, Long> {
}
