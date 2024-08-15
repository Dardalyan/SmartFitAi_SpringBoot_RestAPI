package com.SmartFitAI.repository;

import com.SmartFitAI.model.Exercise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ExerciseRepository extends MongoRepository<Exercise, String> {

    @Query(value = "{'body_part': ?0 }")
    public List<Exercise> findByBodyPart(Exercise.BodyParts bodyPart);

}
