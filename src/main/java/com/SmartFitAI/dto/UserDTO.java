package com.SmartFitAI.dto;

import lombok.Data;

@Data
public class UserDTO
{
    private String email;
    private String password;
    private String username;

    public boolean hasAnyNullField(){
        return email == null || password == null || username == null ? true : false;
    }



    @Override
    public String toString() {
        return String.format("User [email = %s, password = %s, username = %s]",
                email == null ? null : "provided", password == null ? null : "provided", username == null ? null : "provided");
    }

    public String emailInUse() {
        return "Email has already been taken !";
    }

    public String usernameInUse() {
        return "USername has already been taken !";
    }
}
