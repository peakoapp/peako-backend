package com.peakoapp.core.model.dto;

import com.peakoapp.core.model.entity.Entirety;

/**
 * The {@code Payload} interface represents a subset of an {@link Entirety} that directly interacts
 * with the database. All the data transfer objects (DTOs) should implement this interface to
 * provide general POJO conversion.
 *
 * @version 0.1.0
 */
public interface Payload<T extends Entirety> {
    /**
     * Wraps the payload as if it were the entirety. This provides the necessary conversion from a
     * DTO to a persistent object (PO/Entity) managed by its corresponding
     * {@link org.springframework.data.repository.CrudRepository Repository}.
     *
     * @return An instance of the entirety that contains only these details.
     */
    T as();
}
