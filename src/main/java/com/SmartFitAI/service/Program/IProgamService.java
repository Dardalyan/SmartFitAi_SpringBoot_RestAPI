package com.SmartFitAI.service.Program;

import com.SmartFitAI.model.Program;

import java.util.List;

public interface IProgamService {
    public Program getProgram(String uid);
    public boolean createProgram(Program program);
    public boolean updateProgram(Program program);
    public boolean deleteProgram(Program program);
}
