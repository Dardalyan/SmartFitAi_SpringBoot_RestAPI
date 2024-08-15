package com.SmartFitAI.dto;

import lombok.Data;

@Data
public class ContactDTO {

    private String name;
    private String surname;
    private String phone;

    public boolean hasAnyNullField(){
        return name == null || surname == null || phone == null  ? true : false;
    }

    @Override
    public String toString() {
        return "Info[" +
                ", name=" + name +
                ", surname=" + surname +
                ", phone=" + phone +
                ']';
    }
}
