package com.spectralink.user.web.rest;

import com.spectralink.user.domain.EndUser;
import com.spectralink.user.service.EndUserService;
import com.spectralink.user.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.spectralink.user.domain.EndUser}.
 */
@RestController
@RequestMapping("/api")
public class EndUserResource {

    private final Logger log = LoggerFactory.getLogger(EndUserResource.class);

    private static final String ENTITY_NAME = "endUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EndUserService endUserService;

    public EndUserResource(EndUserService endUserService) {
        this.endUserService = endUserService;
    }

    /**
     * {@code POST  /end-users} : Create a new endUser.
     *
     * @param endUser the endUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new endUser, or with status {@code 400 (Bad Request)} if the endUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/end-users")
    public ResponseEntity<EndUser> createEndUser(@Valid @RequestBody EndUser endUser) throws URISyntaxException {
        log.debug("REST request to save EndUser : {}", endUser);
        if (endUser.getId() != null) {
            throw new BadRequestAlertException("A new endUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EndUser result = endUserService.save(endUser);
        return ResponseEntity.created(new URI("/api/end-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /end-users} : Updates an existing endUser.
     *
     * @param endUser the endUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated endUser,
     * or with status {@code 400 (Bad Request)} if the endUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the endUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/end-users")
    public ResponseEntity<EndUser> updateEndUser(@Valid @RequestBody EndUser endUser) throws URISyntaxException {
        log.debug("REST request to update EndUser : {}", endUser);
        if (endUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EndUser result = endUserService.save(endUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, endUser.getId()))
            .body(result);
    }

    /**
     * {@code GET  /end-users} : get all the endUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of endUsers in body.
     */
    @GetMapping("/end-users")
    public ResponseEntity<List<EndUser>> getAllEndUsers(Pageable pageable) {
        log.debug("REST request to get a page of EndUsers");
        Page<EndUser> page = endUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /end-users/:id} : get the "id" endUser.
     *
     * @param id the id of the endUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the endUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/end-users/{id}")
    public ResponseEntity<EndUser> getEndUser(@PathVariable String id) {
        log.debug("REST request to get EndUser : {}", id);
        Optional<EndUser> endUser = endUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(endUser);
    }

    /**
     * {@code DELETE  /end-users/:id} : delete the "id" endUser.
     *
     * @param id the id of the endUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/end-users/{id}")
    public ResponseEntity<Void> deleteEndUser(@PathVariable String id) {
        log.debug("REST request to delete EndUser : {}", id);

        endUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
