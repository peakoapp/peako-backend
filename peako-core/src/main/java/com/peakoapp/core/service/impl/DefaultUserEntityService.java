package com.peakoapp.core.service.impl;

import com.peakoapp.core.model.dto.UserPayload;
import com.peakoapp.core.model.entity.UserEntity;
import com.peakoapp.core.repository.UserEntityRepository;
import com.peakoapp.core.service.EntityService;
import com.peakoapp.core.service.UserEntityService;
import com.peakoapp.core.utils.ArrayUtils;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@code DefaultUserEntityService} class is the default and primary implementation of
 * {@link UserEntityService} that provides basic CRUD operations on {@link UserEntity}.
 *
 * @version 0.1.0
 */
@Transactional(rollbackFor = Exception.class)
@Primary
@Service(value = "defaultUserEntityService")
public class DefaultUserEntityService implements UserEntityService {
    protected static final String[] SPECIAL_PROPS =
            new String[] { "id", "nonDeleted", "nonLocked", "enabled" };

    private final UserEntityRepository userEntityRepository;

    public DefaultUserEntityService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserPayload> getById(Long id) {
        return userEntityRepository.findById(id).map(UserPayload::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserPayload> getByEmail(String email) {
        return userEntityRepository.findByEmail(email).map(UserPayload::new);
    }

    @Override
    public Optional<UserPayload> create(UserPayload payload) {
        return Optional.of(new UserPayload(userEntityRepository.save(payload.as())));
    }

    @Override
    public Optional<UserPayload> updateById(UserPayload payload) {
        return userEntityRepository.findById(payload.getId()).map(entity -> {
            UserEntity updated = payload.as();
            String[] nullProps = EntityService.getNullProperties(updated);
            BeanUtils.copyProperties(updated, entity, ArrayUtils.concat(nullProps, SPECIAL_PROPS));
            return new UserPayload(userEntityRepository.save(entity));
        });
    }

    @Override
    public void unlock(Long id) {
        userEntityRepository.findById(id).ifPresent(entity -> {
            entity.setNonLocked(true);
            entity.setEnabled(entity.getNonDeleted() && entity.getNonLocked());
            userEntityRepository.save(entity);
        });
    }

    @Override
    public void lock(Long id) {
        userEntityRepository.findById(id).ifPresent(entity -> {
            entity.setNonLocked(false);
            entity.setEnabled(false);
            userEntityRepository.save(entity);
        });
    }

    @Override
    public void recover(Long id) {
        userEntityRepository.findById(id).ifPresent(entity -> {
            entity.setNonDeleted(true);
            entity.setEnabled(entity.getNonDeleted() && entity.getNonLocked());
            userEntityRepository.save(entity);
        });
    }

    @Override
    public void markDelete(Long id) {
        userEntityRepository.findById(id).ifPresent(entity -> {
            entity.setNonDeleted(false);
            entity.setEnabled(false);
            userEntityRepository.save(entity);
        });
    }

    @Override
    public void deleteById(Long id) {
        userEntityRepository.deleteById(id);
    }
}
