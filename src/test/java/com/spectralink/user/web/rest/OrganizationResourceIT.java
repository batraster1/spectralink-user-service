package com.spectralink.user.web.rest;

import com.spectralink.user.SpectralinkUserServiceApp;
import com.spectralink.user.config.TestSecurityConfiguration;
import com.spectralink.user.domain.Organization;
import com.spectralink.user.repository.OrganizationRepository;
import com.spectralink.user.service.OrganizationService;

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
 * Integration tests for the {@link OrganizationResource} REST controller.
 */
@SpringBootTest(classes = { SpectralinkUserServiceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class OrganizationResourceIT {

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_ICON = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createEntity() {
        Organization organization = new Organization()
            .organizationName(DEFAULT_ORGANIZATION_NAME)
            .organizationDescription(DEFAULT_ORGANIZATION_DESCRIPTION)
            .organizationIcon(DEFAULT_ORGANIZATION_ICON)
            .created(DEFAULT_CREATED);
        return organization;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createUpdatedEntity() {
        Organization organization = new Organization()
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .organizationDescription(UPDATED_ORGANIZATION_DESCRIPTION)
            .organizationIcon(UPDATED_ORGANIZATION_ICON)
            .created(UPDATED_CREATED);
        return organization;
    }

    @BeforeEach
    public void initTest() {
        organizationRepository.deleteAll();
        organization = createEntity();
    }

    @Test
    public void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();
        // Create the Organization
        restOrganizationMockMvc.perform(post("/api/organizations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrganizationName()).isEqualTo(DEFAULT_ORGANIZATION_NAME);
        assertThat(testOrganization.getOrganizationDescription()).isEqualTo(DEFAULT_ORGANIZATION_DESCRIPTION);
        assertThat(testOrganization.getOrganizationIcon()).isEqualTo(DEFAULT_ORGANIZATION_ICON);
        assertThat(testOrganization.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    public void createOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization with an existing ID
        organization.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc.perform(post("/api/organizations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkOrganizationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setOrganizationName(null);

        // Create the Organization, which fails.


        restOrganizationMockMvc.perform(post("/api/organizations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);

        // Get all the organizationList
        restOrganizationMockMvc.perform(get("/api/organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId())))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME)))
            .andExpect(jsonPath("$.[*].organizationDescription").value(hasItem(DEFAULT_ORGANIZATION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].organizationIcon").value(hasItem(DEFAULT_ORGANIZATION_ICON)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));
    }
    
    @Test
    public void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.save(organization);

        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId()))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME))
            .andExpect(jsonPath("$.organizationDescription").value(DEFAULT_ORGANIZATION_DESCRIPTION))
            .andExpect(jsonPath("$.organizationIcon").value(DEFAULT_ORGANIZATION_ICON))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()));
    }
    @Test
    public void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrganization() throws Exception {
        // Initialize the database
        organizationService.save(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findById(organization.getId()).get();
        updatedOrganization
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .organizationDescription(UPDATED_ORGANIZATION_DESCRIPTION)
            .organizationIcon(UPDATED_ORGANIZATION_ICON)
            .created(UPDATED_CREATED);

        restOrganizationMockMvc.perform(put("/api/organizations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrganization)))
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testOrganization.getOrganizationDescription()).isEqualTo(UPDATED_ORGANIZATION_DESCRIPTION);
        assertThat(testOrganization.getOrganizationIcon()).isEqualTo(UPDATED_ORGANIZATION_ICON);
        assertThat(testOrganization.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    public void updateNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc.perform(put("/api/organizations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteOrganization() throws Exception {
        // Initialize the database
        organizationService.save(organization);

        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Delete the organization
        restOrganizationMockMvc.perform(delete("/api/organizations/{id}", organization.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
