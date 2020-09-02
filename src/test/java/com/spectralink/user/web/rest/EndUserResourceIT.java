package com.spectralink.user.web.rest;

import com.spectralink.user.SpectralinkUserServiceApp;
import com.spectralink.user.config.TestSecurityConfiguration;
import com.spectralink.user.domain.EndUser;
import com.spectralink.user.repository.EndUserRepository;
import com.spectralink.user.service.EndUserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EndUserResource} REST controller.
 */
@SpringBootTest(classes = { SpectralinkUserServiceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EndUserResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EndUserRepository endUserRepository;

    @Autowired
    private EndUserService endUserService;

    @Autowired
    private MockMvc restEndUserMockMvc;

    private EndUser endUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EndUser createEntity() {
        EndUser endUser = new EndUser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .created(DEFAULT_CREATED);
        return endUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EndUser createUpdatedEntity() {
        EndUser endUser = new EndUser()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .created(UPDATED_CREATED);
        return endUser;
    }

    @BeforeEach
    public void initTest() {
        endUserRepository.deleteAll();
        endUser = createEntity();
    }

    @Test
    public void createEndUser() throws Exception {
        int databaseSizeBeforeCreate = endUserRepository.findAll().size();
        // Create the EndUser
        restEndUserMockMvc.perform(post("/api/end-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endUser)))
            .andExpect(status().isCreated());

        // Validate the EndUser in the database
        List<EndUser> endUserList = endUserRepository.findAll();
        assertThat(endUserList).hasSize(databaseSizeBeforeCreate + 1);
        EndUser testEndUser = endUserList.get(endUserList.size() - 1);
        assertThat(testEndUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEndUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEndUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEndUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEndUser.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    public void createEndUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = endUserRepository.findAll().size();

        // Create the EndUser with an existing ID
        endUser.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEndUserMockMvc.perform(post("/api/end-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endUser)))
            .andExpect(status().isBadRequest());

        // Validate the EndUser in the database
        List<EndUser> endUserList = endUserRepository.findAll();
        assertThat(endUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = endUserRepository.findAll().size();
        // set the field null
        endUser.setFirstName(null);

        // Create the EndUser, which fails.


        restEndUserMockMvc.perform(post("/api/end-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endUser)))
            .andExpect(status().isBadRequest());

        List<EndUser> endUserList = endUserRepository.findAll();
        assertThat(endUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEndUsers() throws Exception {
        // Initialize the database
        endUserRepository.save(endUser);

        // Get all the endUserList
        restEndUserMockMvc.perform(get("/api/end-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endUser.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));
    }
    
    @Test
    public void getEndUser() throws Exception {
        // Initialize the database
        endUserRepository.save(endUser);

        // Get the endUser
        restEndUserMockMvc.perform(get("/api/end-users/{id}", endUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(endUser.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()));
    }
    @Test
    public void getNonExistingEndUser() throws Exception {
        // Get the endUser
        restEndUserMockMvc.perform(get("/api/end-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEndUser() throws Exception {
        // Initialize the database
        endUserService.save(endUser);

        int databaseSizeBeforeUpdate = endUserRepository.findAll().size();

        // Update the endUser
        EndUser updatedEndUser = endUserRepository.findById(endUser.getId()).get();
        updatedEndUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .created(UPDATED_CREATED);

        restEndUserMockMvc.perform(put("/api/end-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEndUser)))
            .andExpect(status().isOk());

        // Validate the EndUser in the database
        List<EndUser> endUserList = endUserRepository.findAll();
        assertThat(endUserList).hasSize(databaseSizeBeforeUpdate);
        EndUser testEndUser = endUserList.get(endUserList.size() - 1);
        assertThat(testEndUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEndUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEndUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEndUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEndUser.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    public void updateNonExistingEndUser() throws Exception {
        int databaseSizeBeforeUpdate = endUserRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEndUserMockMvc.perform(put("/api/end-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endUser)))
            .andExpect(status().isBadRequest());

        // Validate the EndUser in the database
        List<EndUser> endUserList = endUserRepository.findAll();
        assertThat(endUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteEndUser() throws Exception {
        // Initialize the database
        endUserService.save(endUser);

        int databaseSizeBeforeDelete = endUserRepository.findAll().size();

        // Delete the endUser
        restEndUserMockMvc.perform(delete("/api/end-users/{id}", endUser.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EndUser> endUserList = endUserRepository.findAll();
        assertThat(endUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
