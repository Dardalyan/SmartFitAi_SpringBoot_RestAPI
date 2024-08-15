package com.SmartFitAI.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "user")
public class User {

    @Id
    @Indexed(unique = true)
    @JsonIgnore
    private String id;

    @Field("username")
    private String username;

    @Field("password")
    @JsonIgnore
    private String password;

    @Field("email")
    private String email;

    @Override
    public String toString(){
        return String.format("User[id=%s , username=%s,  email=%s]",id,username,email);
    }



}
