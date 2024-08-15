package com.SmartFitAI.repository;

import com.SmartFitAI.model.Program;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgramRepository extends MongoRepository<Program, String> {
}
