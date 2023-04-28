package com.youtube.jwt.dao;


import com.youtube.jwt.entity.UrlCount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlCountDao extends MongoRepository<UrlCount, String> {
    UrlCount findByUrl(String url);
}
