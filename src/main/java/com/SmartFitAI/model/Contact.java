package com.SmartFitAI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "contact")
public class Contact {

    @Id
    @Indexed(unique=true)
    String uid;
    @Field("phone")
    String phone  ;
    @Field("name")
    String name   ;
    @Field("surname")
    String surname;

}
