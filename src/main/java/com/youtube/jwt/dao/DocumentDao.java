package com.youtube.jwt.dao;

import com.youtube.jwt.entity.Documents;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentDao extends MongoRepository<Documents, String> {
}
