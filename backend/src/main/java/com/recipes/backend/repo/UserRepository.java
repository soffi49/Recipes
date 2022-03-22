package com.recipes.backend.repo;

import com.recipes.backend.repo.domain.UserDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserDTO, Long> {

    Optional<UserDTO> findByUsername(String username);
    Optional<UserDTO> findByToken(String token);
}
