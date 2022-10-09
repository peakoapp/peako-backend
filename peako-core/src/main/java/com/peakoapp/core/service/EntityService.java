package com.peakoapp.core.service;

import com.peakoapp.core.model.dto.Payload;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * The {@code EntityService} interface defines the most basic CRUD methods on the specified entity.
 * It's important that the payload data passed to this service should be correct in the sense that
 * no more modifications of data should be done inside this service. It's acceptable to merge data
 * structures, but it's unacceptable to change the values of the properties of the given payload.
 *
 * @param <T> The payload of an entity.
 *
 * @version 0.1.0
 */
public interface EntityService<T extends Payload<?>> {
    /**
     * Obtains the payload by looking for the given primary key.
     *
     * @param id The primary key of the table/entity.
     * @return The payload of the entity.
     */
    Optional<T> getById(Long id);

    /**
     * Creates a new entity with the values in the given payload.
     *
     * @param payload The payload object that contains the new values.
     * @return The payload the newly created entity.
     */
    Optional<T> create(T payload);

    /**
     * Updates the entity identified by the primary key with the values in the given payload.
     *
     * @param payload The payload object that contains new values and primary key.
     * @return The payload of the updated entity.
     */
    Optional<T> updateById(T payload);

    /**
     * Deletes the entity identified by the primary key permanently from the database.
     *
     * @param id The primary key of the table/entity.
     */
    void deleteById(Long id);

    /**
     * Finds the names of the properties of the source whose values are null.
     *
     * @param source The object that contains 0 or more null properties.
     * @return An array of strings that correspond to null property names.
     */
    static String[] getNullProperties(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object val = src.getPropertyValue(pd.getName());
            if (val == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
