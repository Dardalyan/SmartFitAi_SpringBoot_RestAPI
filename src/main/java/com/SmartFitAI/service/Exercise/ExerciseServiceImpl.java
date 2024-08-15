package com.SmartFitAI.service.Exercise;

import com.SmartFitAI.model.Exercise;
import com.SmartFitAI.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements IExerciseService{

    private ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public Exercise getExerciseById(String uid) {
        return exerciseRepository.findById(uid).orElse(null);
    }

    @Override
    public boolean createExercise(Exercise exercise) {
        try{
            exerciseRepository.save(exercise);
            return  true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Exercise> getExercisesByBodyparts(Exercise.BodyParts bodyPart) {
        return exerciseRepository.findByBodyPart(bodyPart);
    }
}
