package com.SmartFitAI.service.User;

import com.SmartFitAI.model.User;
import com.SmartFitAI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements  IUserService{


    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserbyId(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUserbyEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<String> allUserIDs() {
        List<User> users = userRepository.findAll();
        List<String> userIDs = new ArrayList<>();
        for(User user : users)
            userIDs.add(user.getId());
        return userIDs;
    }

    @Override
    public User getUserbyUsername (String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean createUser(User user) {
        try{
            User newUser = userRepository.save(user);
            if(newUser!=null) return true;
            else return false;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try{
            if(userRepository.findById(user.getId()) != null){
                userRepository.save(user);
                return true;
            }else return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }
}
