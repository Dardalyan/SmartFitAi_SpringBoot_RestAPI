package com.SmartFitAI.controller.auth;

import com.SmartFitAI.repository.UserRepository;
import com.SmartFitAI.service.User.IUserService;
import com.SmartFitAI.dto.UserDTO;
import com.manage.configuration.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import com.SmartFitAI.model.User;

import java.util.HashMap;
import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private IUserService userService;
    private  AuthenticationManager authenticationManager;
    private JWTGenerator jwtGenerator;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public AuthController(IUserService userService, JWTGenerator jwtGenerator,AuthenticationManager authenticationManager,
    PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap> login(@RequestBody UserDTO userDTO){
        HashMap<String,Object>response = new HashMap<>();

        // This one triggers UserDetailService.loadByUsername() method !
        // Thus, our User model (according to your control in "loadByUsername()") is already checked in terms of username and password !
        // Password must be stored in encoded version !!
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword()));
        if(!authentication.getName().equals(userDTO.getUsername()))
        {
            response.put("alert","Username is not correct !");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token  = jwtGenerator.generate(authentication);

        response.put("token",token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // POST REQUEST
    @PostMapping("/register")
    public ResponseEntity<HashMap> register(@RequestBody UserDTO userDTO){
        HashMap<String,Object>response = new HashMap<>();

        if(userDTO.hasAnyNullField()){
            response.put("alert","All fields are required !");
            response.put("request",userDTO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if(userService.getUserbyEmail(userDTO.getEmail()) != null){
            response.put("alert",userDTO.emailInUse());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if(userService.getUserbyUsername(userDTO.getUsername())!= null){
            response.put("alert",userDTO.usernameInUse());
            return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST);
        }

        String userID = UUID.randomUUID().toString();

        while(userService.allUserIDs().contains(userID)){
            userID = UUID.randomUUID().toString();
        }

        User user = new User(userID,userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail());
        boolean result = userService.createUser(user);

        if (!result){
            response.put("alert","User creation failed !");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("message","User has been created !");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
