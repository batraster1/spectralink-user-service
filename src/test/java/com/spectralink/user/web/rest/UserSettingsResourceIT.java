package com.spectralink.user.web.rest;

import com.spectralink.user.SpectralinkUserServiceApp;
import com.spectralink.user.config.TestSecurityConfiguration;
import com.spectralink.user.domain.UserSettings;
import com.spectralink.user.repository.UserSettingsRepository;
import com.spectralink.user.service.UserSettingsService;

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
 * Integration tests for the {@link UserSettingsResource} REST controller.
 */
@SpringBootTest(classes = { SpectralinkUserServiceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserSettingsResourceIT {

    private static final String DEFAULT_WALLPAPER = "AAAAAAAAAA";
    private static final String UPDATED_WALLPAPER = "BBBBBBBBBB";

    private static final String DEFAULT_RING_TONE = "AAAAAAAAAA";
    private static final String UPDATED_RING_TONE = "BBBBBBBBBB";

    private static final String DEFAULT_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_VOLUME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private MockMvc restUserSettingsMockMvc;

    private UserSettings userSettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSettings createEntity() {
        UserSettings userSettings = new UserSettings()
            .wallpaper(DEFAULT_WALLPAPER)
            .ringTone(DEFAULT_RING_TONE)
            .volume(DEFAULT_VOLUME)
            .created(DEFAULT_CREATED);
        return userSettings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSettings createUpdatedEntity() {
        UserSettings userSettings = new UserSettings()
            .wallpaper(UPDATED_WALLPAPER)
            .ringTone(UPDATED_RING_TONE)
            .volume(UPDATED_VOLUME)
            .created(UPDATED_CREATED);
        return userSettings;
    }

    @BeforeEach
    public void initTest() {
        userSettingsRepository.deleteAll();
        userSettings = createEntity();
    }

    @Test
    public void createUserSettings() throws Exception {
        int databaseSizeBeforeCreate = userSettingsRepository.findAll().size();
        // Create the UserSettings
        restUserSettingsMockMvc.perform(post("/api/user-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSettings)))
            .andExpect(status().isCreated());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        UserSettings testUserSettings = userSettingsList.get(userSettingsList.size() - 1);
        assertThat(testUserSettings.getWallpaper()).isEqualTo(DEFAULT_WALLPAPER);
        assertThat(testUserSettings.getRingTone()).isEqualTo(DEFAULT_RING_TONE);
        assertThat(testUserSettings.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testUserSettings.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    public void createUserSettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userSettingsRepository.findAll().size();

        // Create the UserSettings with an existing ID
        userSettings.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSettingsMockMvc.perform(post("/api/user-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSettings)))
            .andExpect(status().isBadRequest());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllUserSettings() throws Exception {
        // Initialize the database
        userSettingsRepository.save(userSettings);

        // Get all the userSettingsList
        restUserSettingsMockMvc.perform(get("/api/user-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSettings.getId())))
            .andExpect(jsonPath("$.[*].wallpaper").value(hasItem(DEFAULT_WALLPAPER)))
            .andExpect(jsonPath("$.[*].ringTone").value(hasItem(DEFAULT_RING_TONE)))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));
    }
    
    @Test
    public void getUserSettings() throws Exception {
        // Initialize the database
        userSettingsRepository.save(userSettings);

        // Get the userSettings
        restUserSettingsMockMvc.perform(get("/api/user-settings/{id}", userSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSettings.getId()))
            .andExpect(jsonPath("$.wallpaper").value(DEFAULT_WALLPAPER))
            .andExpect(jsonPath("$.ringTone").value(DEFAULT_RING_TONE))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()));
    }
    @Test
    public void getNonExistingUserSettings() throws Exception {
        // Get the userSettings
        restUserSettingsMockMvc.perform(get("/api/user-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserSettings() throws Exception {
        // Initialize the database
        userSettingsService.save(userSettings);

        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();

        // Update the userSettings
        UserSettings updatedUserSettings = userSettingsRepository.findById(userSettings.getId()).get();
        updatedUserSettings
            .wallpaper(UPDATED_WALLPAPER)
            .ringTone(UPDATED_RING_TONE)
            .volume(UPDATED_VOLUME)
            .created(UPDATED_CREATED);

        restUserSettingsMockMvc.perform(put("/api/user-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserSettings)))
            .andExpect(status().isOk());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
        UserSettings testUserSettings = userSettingsList.get(userSettingsList.size() - 1);
        assertThat(testUserSettings.getWallpaper()).isEqualTo(UPDATED_WALLPAPER);
        assertThat(testUserSettings.getRingTone()).isEqualTo(UPDATED_RING_TONE);
        assertThat(testUserSettings.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testUserSettings.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    public void updateNonExistingUserSettings() throws Exception {
        int databaseSizeBeforeUpdate = userSettingsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSettingsMockMvc.perform(put("/api/user-settings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSettings)))
            .andExpect(status().isBadRequest());

        // Validate the UserSettings in the database
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUserSettings() throws Exception {
        // Initialize the database
        userSettingsService.save(userSettings);

        int databaseSizeBeforeDelete = userSettingsRepository.findAll().size();

        // Delete the userSettings
        restUserSettingsMockMvc.perform(delete("/api/user-settings/{id}", userSettings.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSettings> userSettingsList = userSettingsRepository.findAll();
        assertThat(userSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
