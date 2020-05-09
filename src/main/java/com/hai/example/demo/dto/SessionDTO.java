package com.hai.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SessionDTO implements Serializable {
    private long sessionId;
    private String sessionUuid;
    private String sessionContent;
    private String sessionDate; //this field not existed in mapping

    //default display in mapping
    public String getSessionDate(){
        return "Date ngay";
    }
}