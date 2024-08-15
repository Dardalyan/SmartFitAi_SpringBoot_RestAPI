package com.SmartFitAI.service.Exercise;

import com.SmartFitAI.model.Exercise;

import java.util.List;

public interface IExerciseService {
    public List<Exercise> getAllExercises();
    public Exercise getExerciseById(String uid);
    public boolean createExercise(Exercise exercise);
    public List<Exercise> getExercisesByBodyparts(Exercise.BodyParts bodyPart);
}
