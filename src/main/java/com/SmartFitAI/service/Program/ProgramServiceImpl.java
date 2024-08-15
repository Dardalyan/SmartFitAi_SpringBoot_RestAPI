package com.SmartFitAI.service.Program;


import com.SmartFitAI.model.Program;
import com.SmartFitAI.repository.ProgramRepository;
import com.SmartFitAI.service.User.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramServiceImpl implements IProgamService{

    private ProgramRepository programRepository;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }


    @Override
    public Program getProgram(String uid) {
        return programRepository.findById(uid).orElse(null);
    }

    @Override
    public boolean createProgram(Program program) {
        try{
            Program newProgram = programRepository.save(program);
            if(newProgram == null) return false;
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateProgram(Program program) {
        try{
            if(programRepository.findById(program.getUid()) == null) return false;
            programRepository.save(program);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProgram(Program program) {
        try{
            if(programRepository.findById(program.getUid()) == null) return false;
            programRepository.delete(program);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
