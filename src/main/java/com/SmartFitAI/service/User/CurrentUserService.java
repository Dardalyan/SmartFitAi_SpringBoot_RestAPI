package com.SmartFitAI.service.User;


import com.SmartFitAI.model.User;
import com.SmartFitAI.repository.UserRepository;
import com.mongodb.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    @Autowired
    private UserRepository userRepository;


    public User getCurrentUser() {
        // Returns authenticated user !
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }


}
