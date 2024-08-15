package com.SmartFitAI.controller.user;

import com.SmartFitAI.dto.UserDTO;
import com.SmartFitAI.model.User;
import com.SmartFitAI.service.User.CurrentUserService;
import com.SmartFitAI.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;

import static java.awt.SystemColor.info;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private IUserService userService;
    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }


    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(){
        User currentUser = currentUserService.getCurrentUser();
        return new ResponseEntity<>(currentUser,HttpStatus.OK);
    }

    // PUT  REQUEST
    @PutMapping("/update")
    public ResponseEntity<HashMap> updateUser(@RequestBody UserDTO userDTO) {
        HashMap<String, Object> response = new HashMap<>();

        User currentUser = currentUserService.getCurrentUser();

        try{
            if(userDTO.hasAnyNullField()){
                // GET ALL PRIVATE FIELDS
                for(Field userDTOfield : userDTO.getClass().getDeclaredFields()){
                    // SET THEM ACCESSIBLE
                    userDTOfield.setAccessible(true);
                    // CHECK THEIR VALUES
                    if(userDTOfield.get(userDTO) != null ) {
                        // MAKE THEM EQUAL WITH ANOTHER CLASS WHICH HAS THE SAME FIELD NAMES
                        Field userField = currentUser.getClass().getDeclaredField(userDTOfield.getName());
                        userField.setAccessible(true);
                        userField.set(currentUser,userDTOfield.getName() == "password" ? passwordEncoder.encode((CharSequence) userDTOfield.get(userDTO)) :  userDTOfield.get(userDTO));
                    }
                }
            }else{
                for(Field userDTOfield : userDTO.getClass().getDeclaredFields()){
                    userDTOfield.setAccessible(true);
                    Field userField = currentUser.getClass().getDeclaredField(userDTOfield.getName());
                    userField.setAccessible(true);
                    userField.set(currentUser,userDTOfield.getName() == "password" ? passwordEncoder.encode((CharSequence) userDTOfield.get(userDTO)) :  userDTOfield.get(userDTO));
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            response.put("alert", "Error has occured on user update !");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

        boolean result = userService.updateUser(currentUser);
        if(result){
            response.put("message", "User has been updated successfully !");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            response.put("message", "User cannot not be updated !");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE REQUEST
    @DeleteMapping("/delete")
    public ResponseEntity<HashMap> deleteUser(){
        HashMap<String, Object> response = new HashMap<>();

        User currentUser = currentUserService.getCurrentUser();
        boolean result = userService.deleteUser(currentUser);

        if(result){
            response.put("message", "User has been deleted successfully !");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.put("message", "User cannot be deleted !");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
