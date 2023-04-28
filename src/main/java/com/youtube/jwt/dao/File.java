package com.youtube.jwt.dao;

import com.youtube.jwt.entity.ProvidersFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface File extends MongoRepository<ProvidersFile,String> {
}
