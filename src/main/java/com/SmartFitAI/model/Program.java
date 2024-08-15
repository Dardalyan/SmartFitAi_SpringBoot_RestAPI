package com.SmartFitAI.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "program")
public class Program {

    public enum SET{SET_COUNT}

    @Id
    @Indexed(unique = true)
    private String uid;
    @Field("monday")
    private List<HashMap<String,Object>>monday;
    @Field("tuesday")
    private List<HashMap<String,Object>>tuesday;
    @Field("wednesday")
    private List<HashMap<String,Object>>wednesday;
    @Field("thursday")
    private List<HashMap<String,Object>>thursday;
    @Field("friday")
    private List<HashMap<String,Object>>friday;
    @Field("saturday")
    private List<HashMap<String,Object>>saturday;
    @Field("sunday")
    private List<HashMap<String,Object>>sunday;

}
