package com.peakoapp.core.model.vo;

import com.peakoapp.core.model.dto.Payload;

/**
 * The {@code Details} interface represents a subset of a {@link Payload} and is used as a value
 * object (VO) that passes data to or receives data from API calls.
 *
 * @version 0.1.0
 */
public interface Details<T extends Payload<?>> {
    /**
     * Wraps the details as if it were the payload. This provides the necessary conversion from a
     * value object (VO) to a DTO.
     *
     * @return An instance of the payload that contains only these details.
     */
    T as();
}
