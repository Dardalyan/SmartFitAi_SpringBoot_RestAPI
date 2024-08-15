package com.SmartFitAI.service.User;

import com.SmartFitAI.model.User;

import java.util.List;

public interface IUserService {

    public List<User> getAllUsers();
    public User getUserbyId(String id);
    public User getUserbyEmail(String email);
    public User getUserbyUsername(String username);
    public boolean createUser(User user);
    public boolean updateUser(User user);
    public boolean deleteUser(User user);
    public List<String> allUserIDs();

}
