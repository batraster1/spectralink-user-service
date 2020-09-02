package com.spectralink.user.repository;

import com.spectralink.user.domain.UserSettings;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSettingsRepository extends MongoRepository<UserSettings, String> {
}
