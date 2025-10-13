package com.example.Demo_App;

import com.example.Demo_App.Models.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;
    public ToDo createToDo(ToDo toDo)
    {
        return toDoRepository.save(toDo);
    }
    public List<ToDo> getTodo(){
        return toDoRepository.findAll();
    }
    public ToDo getById(Long id)
    {
        return toDoRepository.findById(id).orElseThrow(()->new RuntimeException("Data not Found"));
    }
    public ToDo updateToDo(ToDo toDo)
    {
        return toDoRepository.save(toDo);
    }
    public void deletetoDo(Long id)
    {
        toDoRepository.delete(getById(id));
    }

}
