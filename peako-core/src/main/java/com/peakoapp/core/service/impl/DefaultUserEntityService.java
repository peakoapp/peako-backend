package com.peakoapp.core.service.impl;

import com.peakoapp.core.model.dto.UserPayload;
import com.peakoapp.core.model.entity.UserEntity;
import com.peakoapp.core.repository.UserEntityRepository;
import com.peakoapp.core.service.EntityService;
import com.peakoapp.core.service.UserEntityService;
import com.peakoapp.core.utils.ArrayUtils;
import java.util.Arrays;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserEntityService.class);
    protected static final String[] SPECIAL_PROPS =
            new String[] { "id", "nonDeleted", "nonLocked", "enabled" };

    private final UserEntityRepository userEntityRepository;

    public DefaultUserEntityService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserPayload> getById(Long id) {
        logger.debug("DATABASE::UserEntity::{}: Retrieving", id);
        return userEntityRepository.findById(id).map(UserPayload::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserPayload> getByEmail(String email) {
        logger.debug("DATABASE::UserEntity::{}: Retrieving", email);
        return userEntityRepository.findByEmail(email).map(UserPayload::new);
    }

    @Override
    public Optional<UserPayload> create(UserPayload payload) {
        Optional<UserEntity> user = userEntityRepository.findByEmail(payload.getEmail());
        if (user.isEmpty()) {
            logger.debug("DATABASE::UserEntity::{}: Email available", payload.getEmail());
            logger.debug("DATABASE::UserEntity::{}: Creating a new record", payload.getEmail());
            return Optional.of(new UserPayload(userEntityRepository.save(payload.as())));
        } else {
            logger.debug("DATABASE::UserEntity::{}: Found duplicate email", payload.getEmail());
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserPayload> updateById(UserPayload payload) {
        return userEntityRepository.findById(payload.getId()).map(entity -> {
            logger.debug("DATABASE::UserEntity::{}: Found", entity.getId());
            logger.debug("DATABASE::UserEntity::{}: Updating the account", entity.getId());
            UserEntity updated = payload.as();
            String[] nullProps = EntityService.getNullProperties(updated);
            String[] ignoredProps = ArrayUtils.concat(nullProps, SPECIAL_PROPS);
            logger.debug("DATABASE::UserEntity::{}: Ignoring the null fields {} while updating",
                    Arrays.toString(ignoredProps), entity.getId());
            BeanUtils.copyProperties(updated, entity, ignoredProps);
            return new UserPayload(userEntityRepository.save(entity));
        });
    }

    @Override
    public void unlock(Long id) {
        userEntityRepository.findById(id).ifPresent(entity -> {
            logger.debug("DATABASE::UserEntity::{}: Found", id);
            if (!entity.getNonLocked()) {
                logger.debug("DATABASE::UserEntity::{}: Unlocking the account", entity.getId());
                entity.setNonLocked(true);
                entity.setEnabled(entity.getNonDeleted() && entity.getNonLocked());
                userEntityRepository.save(entity);
            } else {
                logger.debug("DATABASE::User::{}: Ignoring the unlock request because the account"
                        + " was not locked", entity.getId());
            }
        });
    }

    @Override
    public void lock(Long id) {
        userEntityRepository.findById(id).ifPresent(entity -> {
            logger.debug("DATABASE::UserEntity::{}: Found", id);
            if (entity.getNonLocked()) {
                logger.debug("DATABASE::UserEntity::{}: Locking the account", entity.getId());
                entity.setNonLocked(false);
                entity.setEnabled(false);
                userEntityRepository.save(entity);
            } else {
                logger.debug("DATABASE::User::{}: Ignoring the lock request because the account"
                        + " was already locked", entity.getId());
            }
        });
    }

    @Override
    public void recover(Long id) {
        userEntityRepository.findById(id).ifPresent(entity -> {
            logger.debug("DATABASE::UserEntity::{}: Found", id);
            if (!entity.getNonDeleted()) {
                logger.debug("DATABASE::UserEntity::{}: Recovering the account", entity.getId());
                entity.setNonDeleted(true);
                entity.setEnabled(entity.getNonDeleted() && entity.getNonLocked());
                userEntityRepository.save(entity);
            } else {
                logger.debug("DATABASE::User::{}: Ignoring the recover request because the account"
                        + " was not deleted", entity.getId());
            }
        });
    }

    @Override
    public void markDelete(Long id) {
        userEntityRepository.findById(id).ifPresent(entity -> {
            logger.debug("DATABASE::UserEntity::{}: Found", id);
            if (entity.getNonDeleted()) {
                logger.debug("DATABASE::UserEntity::{}: Marking account deleted", entity.getId());
                entity.setNonDeleted(false);
                entity.setEnabled(false);
                userEntityRepository.save(entity);
            } else {
                logger.debug("DATABASE::User::{}: Ignoring the mark delete request because the"
                        + " account was already deleted", entity.getId());
            }
        });
    }

    @Override
    public void deleteById(Long id) {
        logger.debug("DATABASE::UserEntity::{}: Deleting the account permanently regardless of "
                + " existence", id);
        userEntityRepository.deleteById(id);
    }
}
