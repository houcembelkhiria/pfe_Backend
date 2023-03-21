package com.youtube.jwt.dao;

import com.youtube.jwt.entity.RegistrationRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRequestRepository extends MongoRepository<RegistrationRequest,String> {
}
