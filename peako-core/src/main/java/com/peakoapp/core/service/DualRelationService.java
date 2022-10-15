package com.peakoapp.core.service;

/**
 * The {@code DualRelationService} interface, like {@link EntityService}, defines the basic CRUD
 * methods on the relation. Importantly, this should only be used on dual relations, i.e., a
 * relation table must have exactly two foreign keys (same or not).
 *
 * @version 0.1.0
 */
public interface DualRelationService {
    /**
     * Checks if there exists a relation between the two entities identified by the given ids.
     *
     * @param id1 The id of one of the entities.
     * @param id2 The id of the other entity.
     * @return True if they are related; otherwise, false.
     */
    boolean hasRelation(Long id1, Long id2);

    /**
     * Creates a relation between the two entities identified by the given ids.
     *
     * @param id1 The id of one of the entities.
     * @param id2 The id of the other entity to be related with.
     */
    void createRelation(Long id1, Long id2);

    /**
     * Creates the relation between the two entities identified by the given ids.
     *
     * @param id1 The id of one of the entities.
     * @param id2 The id of the other entity.
     */
    void deleteRelation(Long id1, Long id2);
}
