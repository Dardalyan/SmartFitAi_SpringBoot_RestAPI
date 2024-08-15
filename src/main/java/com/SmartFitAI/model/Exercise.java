package com.SmartFitAI.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "exercise")
public class Exercise {

    public static enum BodyParts{ABS,BICEPS,CALVES,CHEST,FOREARMS,GLUTES,HAMSTRING,LATS,QUADS,SHOULDERS,TRAPS,TRICEPS}

    @Id
    @Indexed(unique = true)
    private String name;
    private BodyParts body_part;

    public static boolean isBigPart(BodyParts part){
        return part.equals(BodyParts.CHEST) || part.equals(BodyParts.HAMSTRING) || part.equals(BodyParts.QUADS) || part.equals(BodyParts.LATS)|| part.equals(BodyParts.SHOULDERS);
    }

}
