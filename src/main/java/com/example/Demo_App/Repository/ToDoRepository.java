package com.example.Demo_App.Repository;

import com.example.Demo_App.Models.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo,Long> {
}
