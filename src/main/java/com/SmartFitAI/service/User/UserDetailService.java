package com.SmartFitAI.service.User;

import com.SmartFitAI.model.User;
import com.SmartFitAI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public UserDetailService(UserRepository repo){userRepo=repo;}

    //DaoAuthenticationProvider checks the supplied password with the one it retrieved using the UserDetailsService,
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        //System.out.println("Method: loadUserByUsername() \n USER: " + user);
        if(user==null)
            throw new UsernameNotFoundException("User not found ! ");
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),mapRolesToAuthorities(new ArrayList<>()));// No role yet !
    }
    //  !!!!!!!!!!!!!!!!!!!!
    //  WHEN authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword()));
    //  triggers in auth login controller,
    // it checks  the user credentials (password) by using
    // this security.core.userdetails.User(user.getUsername(),user.getPassword(),mapRolesToAuthorities(new ArrayList<>()));
    // and if mapRolesToAuthorities() is null , we are unauthenticated users !!
    // in first param user.getUsername() is important !! check it in endpoint again
    // using authentication.getName().equals(userDTO.getUsername()) if true -> generate token !!!

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<String> roles){
        return roles.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
}
