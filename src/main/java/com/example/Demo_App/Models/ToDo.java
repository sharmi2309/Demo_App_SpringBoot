package com.example.Demo_App.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jdk.jfr.DataAmount;
import lombok.Data;

@Entity
@Data
public class ToDo {
    @Id
    @GeneratedValue
    Long id;
    String title;
    String description;
    Boolean is_Completed;
}
