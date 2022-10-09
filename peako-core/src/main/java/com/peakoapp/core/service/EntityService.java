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
 *
 * @param <T> The payload of an entity.
 *
 * @version 0.1.0
 */
public interface EntityService<T extends Payload<?>> {
    Optional<T> getById(Long id);

    Optional<T> updateById(T payload);

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
