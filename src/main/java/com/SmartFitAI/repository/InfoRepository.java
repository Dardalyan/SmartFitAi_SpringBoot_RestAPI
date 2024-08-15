package com.SmartFitAI.repository;

import com.SmartFitAI.model.Info;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InfoRepository extends MongoRepository<Info, String> {
}
