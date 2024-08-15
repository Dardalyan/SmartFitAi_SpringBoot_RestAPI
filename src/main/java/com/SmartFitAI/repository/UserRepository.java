package com.SmartFitAI.repository;

import com.SmartFitAI.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface UserRepository extends MongoRepository<User,Integer> {

    @Query("{'id': ?0}")
    public User findById(String id);

    @Query("{'email': ?0}")
    public User findByEmail(String email);

    @Query("{'username': ?0}")
    public User findByUsername(String username);
}
