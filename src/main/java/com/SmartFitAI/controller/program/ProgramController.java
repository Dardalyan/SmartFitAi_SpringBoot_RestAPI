package com.SmartFitAI.controller.program;


import com.SmartFitAI.dto.ProgramDTO;
import com.SmartFitAI.model.Exercise;
import com.SmartFitAI.model.Program;
import com.SmartFitAI.model.User;
import com.SmartFitAI.service.Exercise.IExerciseService;
import com.SmartFitAI.service.Program.IProgamService;
import com.SmartFitAI.service.User.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/user/program")
public class ProgramController {

    private IProgamService programService;
    private CurrentUserService currentUserService;
    private IExerciseService exerciseService;

    @Autowired
    public ProgramController(IProgamService progamService, CurrentUserService currentUserService, IExerciseService exerciseService) {
        this.programService = progamService;
        this.currentUserService = currentUserService;
        this.exerciseService = exerciseService;
    }

    private ResponseEntity<HashMap>createOrUpdate(HashMap<String,Object>response,ProgramDTO programDTO,Program program,boolean isUpdated){
        try{
            // GET ALL PRIVATE FIELDS OF programDTO
            for(Field programDTOfield : programDTO.getClass().getDeclaredFields()) {
                // SET THEM ACCESSIBLE
                programDTOfield.setAccessible(true);

                // DEFINE A NEW FIELD OF PROGRAM CLASS (of newProgram instance !!!)
                Field newProgramField = program.getClass().getDeclaredField(programDTOfield.getName());
                // SET THAT ACCESSIBLE
                newProgramField.setAccessible(true);

                if(programDTOfield.get(programDTO) != null) {

                    // CREATE AN OBJECT WITH THE SAME TYPE OF THIS FIELD TO MAKE THEM EQUALS LATER ( !! newProgramField TYPE == program_day !!)
                    List<HashMap<String,Object>> dailyProgram = new ArrayList<>();

                    // GET THE VALUE OF EACH FIELD OF programDTO instance
                    List<String> exercise_names = (List<String>) programDTOfield.get(programDTO);

                        // GET EACH STRING NAME (represents the id of PROGRAMS ) FROM THE LIST COMES FROM HASH MAP
                        for(String exercise_name :  exercise_names){

                            Exercise exercise = exerciseService.getExerciseById(exercise_name);

                            // THIS IS WHAT PROGRAM FIELD HOLDS
                            HashMap<String,Object>dailyProgram_data= new HashMap<>();

                            dailyProgram_data.put("exercise_name",exercise.getName());
                            dailyProgram_data.put(Program.SET.SET_COUNT.toString(), Exercise.isBigPart(exercise.getBody_part()) ? 4 :3);
                            dailyProgram_data.put("body_part",exercise.getBody_part());

                            dailyProgram.add(dailyProgram_data);
                        }
                    newProgramField.set(program,dailyProgram);

                }
                else{ // IF programDTOfield.get(programDTO) == null
                    newProgramField.set(program,new ArrayList<>());
                }
            }

            boolean result = programService.createProgram(program);

            if(!result){
                response.put("message","Program cannot not be created !");
                new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("message",isUpdated ? "Program has been updated successfully !" : "Program has been created succesfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("alert","Error has occured on program creation !");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<HashMap> createProgram(@RequestBody ProgramDTO programDTO) {
        HashMap<String, Object> response = new HashMap<>();
        User currentUser = currentUserService.getCurrentUser();
        Program newProgram = new Program();
        newProgram.setUid(currentUser.getId());
        return createOrUpdate(response,programDTO,newProgram,false);
    }

    @PutMapping("/update")
    public ResponseEntity<HashMap>updateProgram(@RequestBody ProgramDTO programDTO) {
        HashMap<String, Object> response = new HashMap<>();
        User currentUser = currentUserService.getCurrentUser();
        Program existingProgram = programService.getProgram(currentUser.getId());
        if(existingProgram == null) {
            response.put("alert","Program does not exist !");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return createOrUpdate(response,programDTO,existingProgram,true);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HashMap>deleteProgram(@RequestBody ProgramDTO programDTO) {
        HashMap<String, Object> response = new HashMap<>();
        User currentUser = currentUserService.getCurrentUser();
        try{

            Program currentProgram = programService.getProgram(currentUser.getId());
            boolean result = programService.deleteProgram(currentProgram);
            if(result){
                response.put("message","Program has been deleted !");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                response.put("alert","Program cannot be deleted !");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("alert","Error has occured on program deletion !");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<HashMap>getCurrentProgram() {
        HashMap<String, Object> response = new HashMap<>();
        User currentUser = currentUserService.getCurrentUser();
        try {
            Program currentProgram = programService.getProgram(currentUser.getId());
            response.put("currentProgram",currentProgram);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            response.put("alert","Error has occured on program retrieval !");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{day}")
    public ResponseEntity<HashMap>getCurrentProgram(@PathVariable String day) {
        HashMap<String, Object> response = new HashMap<>();
        User currentUser = currentUserService.getCurrentUser();
        try {
            // Get the name of the day correctly like sunday,monday... etc.
            Program currentProgram = programService.getProgram(currentUser.getId());
            Field wishListField = currentProgram.getClass().getDeclaredField(day);
            wishListField.setAccessible(true);
            response.put(wishListField.getName(), wishListField.get(currentProgram));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            response.put("alert","Error has occured on program retrieval !");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
