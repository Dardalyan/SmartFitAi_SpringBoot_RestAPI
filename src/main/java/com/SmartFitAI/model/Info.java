package com.SmartFitAI.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Document(collection = "info")
public class Info {


    public static enum Gender{MALE,FEMALE}
    public static enum Target{LOOSE_WEIGHT,GAIN_WEIGHT,COVER_WEIGHT}
    public static enum ActivityIntensity{SLIGHTLY_OR_NONE,SLIGHTLY,MODERATELY,VERY,EXTREMELY}
    public static enum BodyParts{ALL,ABS,BICEPS,CALVES,CHEST,FOREARMS,GLUTES,HAMSTRING,LATS,QUADS,SHOULDERS,TRAPS,TRICEPS}

    @Id
    @Indexed(unique=true)
    private String uid;
    @Field("age")
    private int age ;
    @Field("gender")
    private Gender gender;
    @Field("weight")
    private double weight;
    @Field("height")
    private double height ;
    @Field("target")
    private Target target;
    @Field("bmr")
    private double bmr;
    @Field("activity_intensity")
    private ActivityIntensity activity_intensity;
    @Field("calorie_bound")
    private double calorie_bound;
    @Field("target_body_parts")
    private List<BodyParts> target_body_parts;

    public Info(String uid,int age, Gender gender,
                double weight, double height,
                Target target, ActivityIntensity activity_intensity
            , List<BodyParts> target_body_parts)

    {
        this.uid = uid;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.target = target;
        this.activity_intensity = activity_intensity;
        this.target_body_parts = target_body_parts;

        this.bmr = calculateBMR();
        this.calorie_bound = calculateCalBound();

    }

    private double calculateBMR(){
        return gender == Gender.MALE ? (66.5 + (9.563*weight) + (1.850*height) + (6.775*age)) : (66.5 + (13.75*weight) + (5.0003*height) + (4.676*age));
    }

    private double calculateCalBound(){
       if(activity_intensity == ActivityIntensity.SLIGHTLY_OR_NONE)
           return bmr*1.2;
       else if(activity_intensity == ActivityIntensity.SLIGHTLY)
           return bmr*1.375;
       else if(activity_intensity == ActivityIntensity.MODERATELY)
           return bmr*1.55;
       else if(activity_intensity == ActivityIntensity.VERY)
           return bmr*1.725;
       else
           return bmr*1.9;
    }

    public static String fieldsLike(){
        return
        String.format(
                "["+
                "age = %s, " +
                "gender = %s, " +
                "weight = %s, " +
                "height = %s, " +
                "target = %s, " +
                "activity_intensity = %s, " +
                "target_body_parts = %s "+ "]"
                ,
                "int",
                ("Choose one of "+ genderValues()),
                "double",
                "double",
                ("Choose one of "+targetValues()),
                ("Choose one of "+activityIntensityValues()),
                ("Add your parts from "+ bodyPartsValues())
        );

    }

    private static String genderValues(){
        List<String >gv = new ArrayList<>();
        for(Gender gender : Gender.values())
        {
            gv.add(gender.toString());
        }
        return gv.toString();
    }

    private static String activityIntensityValues(){
        List<String >aiv = new ArrayList<>();
        for(ActivityIntensity activityIntensity : ActivityIntensity.values())
        {
            aiv.add(activityIntensity.toString());
        }
        return aiv.toString();
    }

    private static String targetValues(){
        List<String >trgtv = new ArrayList<>();
        for(Target target : Target.values())
        {
            trgtv.add(target.toString());
        }
        return trgtv.toString();
    }

    private static String bodyPartsValues(){
        List<String >bpv = new ArrayList<>();
        for(BodyParts bodyPart : BodyParts.values())
        {
            bpv.add(bodyPart.toString());
        }
        return bpv.toString();
    }

}
