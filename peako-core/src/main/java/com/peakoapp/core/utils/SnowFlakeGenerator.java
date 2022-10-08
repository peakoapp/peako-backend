package com.peakoapp.core.utils;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * The SnowFlakeGenerator class is a custom id generator.
 *
 * @version 0.1.0
 */
public class SnowFlakeGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor,
                                 Object o) throws HibernateException {
        return new SnowFlake(0, 1).nextId();
    }
}
