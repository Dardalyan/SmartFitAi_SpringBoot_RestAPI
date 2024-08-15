package com.SmartFitAI.controller.exercise;


import com.SmartFitAI.model.Exercise;
import com.SmartFitAI.service.Exercise.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    private IExerciseService exerciseService;


    @Autowired
    public ExerciseController(IExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    /* Just for the creation of these all exercises on MongoDB
        @PostMapping("/create")
    public void createExercises() {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Crunch",Exercise.BodyParts.ABS));
        exercises.add(new Exercise("Reverse Crunch",Exercise.BodyParts.ABS));
        exercises.add(new Exercise("Hanging Knee Raises",Exercise.BodyParts.ABS));
        exercises.add(new Exercise("Plank",Exercise.BodyParts.ABS));
        exercises.add(new Exercise("V-Ups",Exercise.BodyParts.ABS));
        exercises.add(new Exercise("Biceps Curl",Exercise.BodyParts.BICEPS));
        exercises.add(new Exercise("Barbell Curl",Exercise.BodyParts.BICEPS));
        exercises.add(new Exercise("Hammer Curl",Exercise.BodyParts.BICEPS));
        exercises.add(new Exercise("Consantration Curl",Exercise.BodyParts.BICEPS));
        exercises.add(new Exercise("Incline Dumbell Curl",Exercise.BodyParts.BICEPS));
        exercises.add(new Exercise("Calf Raises",Exercise.BodyParts.CALVES));
        exercises.add(new Exercise("Single Leg Calf Raise",Exercise.BodyParts.CALVES));
        exercises.add(new Exercise("Standing Dumbell Calf Raise",Exercise.BodyParts.CALVES));
        exercises.add(new Exercise("Standing Barbell Calf Raise",Exercise.BodyParts.CALVES));
        exercises.add(new Exercise("Bench Press",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Incline Bench Press",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Decline Bench Press",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Dumbell Chest Press",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Incline Dummbell Press",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Decline Dumbell Press",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Dumbell Fly",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Dumbell Pullover",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Cable Crossover",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Push Up",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Diamond Push Up",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Decline Push Up",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Close Grip Bench Press",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Dips",Exercise.BodyParts.CHEST));
        exercises.add(new Exercise("Wrist Curl",Exercise.BodyParts.FOREARMS));
        exercises.add(new Exercise("Reverse Barbell Curl",Exercise.BodyParts.FOREARMS));
        exercises.add(new Exercise("Glute Bridge",Exercise.BodyParts.GLUTES));
        exercises.add(new Exercise("Barbell Hip Thrust",Exercise.BodyParts.GLUTES));
        exercises.add(new Exercise("Romanian Deadlift",Exercise.BodyParts.GLUTES));
        exercises.add(new Exercise("Deadlift",Exercise.BodyParts.GLUTES));
        exercises.add(new Exercise("Split Squats",Exercise.BodyParts.HAMSTRING));
        exercises.add(new Exercise("Leg Curl",Exercise.BodyParts.HAMSTRING));
        exercises.add(new Exercise("Kettlebell Swing",Exercise.BodyParts.HAMSTRING));
        exercises.add(new Exercise("Side Lunger",Exercise.BodyParts.HAMSTRING));
        exercises.add(new Exercise("Squat",Exercise.BodyParts.HAMSTRING));
        exercises.add(new Exercise("Pull-Up",Exercise.BodyParts.LATS));
        exercises.add(new Exercise("Chin-Up",Exercise.BodyParts.LATS));
        exercises.add(new Exercise("Dumbell Pullover",Exercise.BodyParts.LATS));
        exercises.add(new Exercise("Lat Pull Down",Exercise.BodyParts.LATS));
        exercises.add(new Exercise("Seated Cable Row",Exercise.BodyParts.LATS));
        exercises.add(new Exercise("T-Bar Row",Exercise.BodyParts.LATS));
        exercises.add(new Exercise("Bent Over Row",Exercise.BodyParts.LATS));
        exercises.add(new Exercise("One Arm Dumbell Row",Exercise.BodyParts.LATS));
        exercises.add(new Exercise("Romanian Deadlift",Exercise.BodyParts.LATS));
        exercises.add(new Exercise("Deadlift",Exercise.BodyParts.LATS));
        exercises.add(new Exercise("Leg Press",Exercise.BodyParts.QUADS));
        exercises.add(new Exercise("Squat",Exercise.BodyParts.QUADS));
        exercises.add(new Exercise("Leg Extension",Exercise.BodyParts.QUADS));
        exercises.add(new Exercise("Pistol Squat",Exercise.BodyParts.QUADS));
        exercises.add(new Exercise("Over Head Press",Exercise.BodyParts.SHOULDERS));
        exercises.add(new Exercise("Dumbell Shoulder Press",Exercise.BodyParts.SHOULDERS));
        exercises.add(new Exercise("Front Raise",Exercise.BodyParts.SHOULDERS));
        exercises.add(new Exercise("Side Lateral Raise",Exercise.BodyParts.SHOULDERS));
        exercises.add(new Exercise("Reverse Fly",Exercise.BodyParts.SHOULDERS));
        exercises.add(new Exercise("Face Pull",Exercise.BodyParts.SHOULDERS));
        exercises.add(new Exercise("Dumbell Shrug",Exercise.BodyParts.TRAPS));
        exercises.add(new Exercise("Barbell Shrug",Exercise.BodyParts.TRAPS));
        exercises.add(new Exercise("Farmers Walk",Exercise.BodyParts.TRAPS));
        exercises.add(new Exercise("Deadlift",Exercise.BodyParts.TRAPS));
        exercises.add(new Exercise("Rack Pull",Exercise.BodyParts.TRAPS));
        exercises.add(new Exercise("Cable Push Down",Exercise.BodyParts.TRICEPS));
        exercises.add(new Exercise("Close Grip Bench Press",Exercise.BodyParts.TRICEPS));
        exercises.add(new Exercise("Dips",Exercise.BodyParts.TRICEPS));
        exercises.add(new Exercise("Dumbell Triceps Extension",Exercise.BodyParts.TRICEPS));
        exercises.add(new Exercise("Barbell Triceps Extension",Exercise.BodyParts.TRICEPS));
        exercises.add(new Exercise("Diamond Push Up",Exercise.BodyParts.TRICEPS));


        for (Exercise e: exercises) {
            exerciseService.createExercise(e);
        }
    }
     */

    @GetMapping("/list/all")
    public List<Exercise> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("/list/{body_part}")
    public List<Exercise>getExercisesByParts(@PathVariable Exercise.BodyParts body_part) {
        return exerciseService.getExercisesByBodyparts(body_part);
    }
}
