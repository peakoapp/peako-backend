package com.peakoapp.core.repository;

import com.peakoapp.core.model.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The {@code UserEntityRepository} defines interfaces of CRUD operations on {@code UserEntity}.
 *
 * @version 0.1.0
 */
public interface UserEntityRepository extends PagingAndSortingRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
