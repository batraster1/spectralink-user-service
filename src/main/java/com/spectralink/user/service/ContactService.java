package com.spectralink.user.service;

import com.spectralink.user.domain.Contact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Contact}.
 */
public interface ContactService {

    /**
     * Save a contact.
     *
     * @param contact the entity to save.
     * @return the persisted entity.
     */
    Contact save(Contact contact);

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Contact> findAll(Pageable pageable);


    /**
     * Get the "id" contact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Contact> findOne(String id);

    /**
     * Delete the "id" contact.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
