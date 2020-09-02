package com.spectralink.user.service.impl;

import com.spectralink.user.service.EndUserService;
import com.spectralink.user.domain.EndUser;
import com.spectralink.user.repository.EndUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EndUser}.
 */
@Service
public class EndUserServiceImpl implements EndUserService {

    private final Logger log = LoggerFactory.getLogger(EndUserServiceImpl.class);

    private final EndUserRepository endUserRepository;

    public EndUserServiceImpl(EndUserRepository endUserRepository) {
        this.endUserRepository = endUserRepository;
    }

    /**
     * Save a endUser.
     *
     * @param endUser the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EndUser save(EndUser endUser) {
        log.debug("Request to save EndUser : {}", endUser);
        return endUserRepository.save(endUser);
    }

    /**
     * Get all the endUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<EndUser> findAll(Pageable pageable) {
        log.debug("Request to get all EndUsers");
        return endUserRepository.findAll(pageable);
    }


    /**
     * Get one endUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<EndUser> findOne(String id) {
        log.debug("Request to get EndUser : {}", id);
        return endUserRepository.findById(id);
    }

    /**
     * Delete the endUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete EndUser : {}", id);

        endUserRepository.deleteById(id);
    }
}
