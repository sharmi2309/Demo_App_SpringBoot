package com.example.Demo_App.Repository;

import com.example.Demo_App.Models.ToDo;

import java.util.List;

public interface ToDoCriteriaRepository {
    List<ToDo> searchToDos(String title, Boolean isCompleted);
}
