package com.SmartFitAI.dto;

import com.SmartFitAI.model.Info;
import lombok.Data;

import java.util.List;

@Data
public class InfoDTO {

    private int age ;
    private Info.Gender gender;
    private double weight;
    private double height ;
    private Info.Target target;
    private Info.ActivityIntensity activity_intensity;
    private List<Info.BodyParts> target_body_parts;

    public boolean hasAnyNullField(){
        return age == 0 || gender == null || weight == 0 || height == 0 || target == null || activity_intensity == null || target_body_parts == null ? true : false;
    }

    @Override
    public String toString() {
        return "Info[" +
                "age=" + age +
                ", gender=" + gender +
                ", weight=" + weight +
                ", height=" + height +
                ", target=" + target +
                ", activity_intensity=" + activity_intensity +
                ", target_body_parts=" + target_body_parts +
                ']';
    }
}
