package com.org.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher implements Serializable{
    String tno;
    String tname;
    String tsex;
    int tage;
    String teb;
    String tpt;
    String cno1;
    String cno2;
    String cno3;
}
