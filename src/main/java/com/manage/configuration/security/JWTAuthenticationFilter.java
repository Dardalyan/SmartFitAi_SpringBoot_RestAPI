package com.manage.configuration.security;

import com.SmartFitAI.service.User.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private UserDetailService userDetailService;


    // The method used in configuration file, it is set as a jwt filter which is used before normal authentication
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            /*    // TO GET INFO FROM WHICH URL WE GET AN REQUEST
                   System.out.println(request.getRequestURL().toString());
             */
            String token = getJWTFromRequest(request);
            if(StringUtils.hasText(token) && jwtGenerator.validateToken(token)){
                String username = jwtGenerator.getUsernameByToken(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());// IF 2 PARAMS CONSTRUCTOR IS USED
                // (NO AUTHORIY) -> AUTHENTICATED IS FALSE !!
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            System.out.println("Token is invalid or expired !");
        }
        filterChain.doFilter(request,response);
    }


    private String getJWTFromRequest(HttpServletRequest httpServletRequest){
        String bearerToken =  httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;
    }
}
