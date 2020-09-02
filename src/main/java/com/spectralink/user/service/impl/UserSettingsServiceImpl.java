package com.spectralink.user.service.impl;

import com.spectralink.user.service.UserSettingsService;
import com.spectralink.user.domain.UserSettings;
import com.spectralink.user.repository.UserSettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserSettings}.
 */
@Service
public class UserSettingsServiceImpl implements UserSettingsService {

    private final Logger log = LoggerFactory.getLogger(UserSettingsServiceImpl.class);

    private final UserSettingsRepository userSettingsRepository;

    public UserSettingsServiceImpl(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    /**
     * Save a userSettings.
     *
     * @param userSettings the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserSettings save(UserSettings userSettings) {
        log.debug("Request to save UserSettings : {}", userSettings);
        return userSettingsRepository.save(userSettings);
    }

    /**
     * Get all the userSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<UserSettings> findAll(Pageable pageable) {
        log.debug("Request to get all UserSettings");
        return userSettingsRepository.findAll(pageable);
    }


    /**
     * Get one userSettings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<UserSettings> findOne(String id) {
        log.debug("Request to get UserSettings : {}", id);
        return userSettingsRepository.findById(id);
    }

    /**
     * Delete the userSettings by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete UserSettings : {}", id);

        userSettingsRepository.deleteById(id);
    }
}
