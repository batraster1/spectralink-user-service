package com.spectralink.user.service;

import com.spectralink.user.domain.UserSettings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link UserSettings}.
 */
public interface UserSettingsService {

    /**
     * Save a userSettings.
     *
     * @param userSettings the entity to save.
     * @return the persisted entity.
     */
    UserSettings save(UserSettings userSettings);

    /**
     * Get all the userSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserSettings> findAll(Pageable pageable);


    /**
     * Get the "id" userSettings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserSettings> findOne(String id);

    /**
     * Delete the "id" userSettings.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
