package com.spectralink.user.service.impl;

import com.spectralink.user.service.OrganizationService;
import com.spectralink.user.domain.Organization;
import com.spectralink.user.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Organization}.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * Save a organization.
     *
     * @param organization the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Organization save(Organization organization) {
        log.debug("Request to save Organization : {}", organization);
        return organizationRepository.save(organization);
    }

    /**
     * Get all the organizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Organization> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll(pageable);
    }


    /**
     * Get one organization by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Organization> findOne(String id) {
        log.debug("Request to get Organization : {}", id);
        return organizationRepository.findById(id);
    }

    /**
     * Delete the organization by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Organization : {}", id);

        organizationRepository.deleteById(id);
    }
}
