package com.org.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable{
    String sno;
    String spassword;
    String sname;
    String ssex;
    int sage;
    String sdept;
    String token;
    String sid;
    String power;
}
