package com.spectralink.user.repository;

import com.spectralink.user.domain.EndUser;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the EndUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndUserRepository extends MongoRepository<EndUser, String> {
}
