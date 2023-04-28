package com.youtube.jwt.dao;

import com.youtube.jwt.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends MongoRepository<User, String> {
  //  User findByUserName(String userName);

}

