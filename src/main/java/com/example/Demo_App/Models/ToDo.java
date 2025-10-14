package com.example.Demo_App.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jdk.jfr.DataAmount;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
public class ToDo {
    @Id
    @GeneratedValue
    Long id;
    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be blank")
    @Schema(name="title",example="ToDo List")
    String title;
    String description;
    Boolean is_Completed;
}
