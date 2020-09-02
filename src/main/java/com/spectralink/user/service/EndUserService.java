package com.spectralink.user.service;

import com.spectralink.user.domain.EndUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EndUser}.
 */
public interface EndUserService {

    /**
     * Save a endUser.
     *
     * @param endUser the entity to save.
     * @return the persisted entity.
     */
    EndUser save(EndUser endUser);

    /**
     * Get all the endUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EndUser> findAll(Pageable pageable);


    /**
     * Get the "id" endUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EndUser> findOne(String id);

    /**
     * Delete the "id" endUser.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
