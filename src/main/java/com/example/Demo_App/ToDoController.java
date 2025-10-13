package com.example.Demo_App;

import com.example.Demo_App.Models.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/todo")
public class ToDoController {
    @Autowired
    private ToDoService toDoService;
    @GetMapping("/get")
     ResponseEntity<?> getToDo()
    {
        try {
            List<ToDo> getallTodo = toDoService.getTodo();
            return new ResponseEntity<>(getallTodo,HttpStatus.OK);
        }
        catch (RuntimeException e)
        {
            Map<String,String>response = new HashMap<>();
            response.put("message","Data Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }
    //PathVariable
    @GetMapping("/{id}")
    ResponseEntity<?> getToDoById(@PathVariable Long id)
    {
        try {
            ToDo getTodo = toDoService.getById(id);
            return new ResponseEntity<>(getTodo, HttpStatus.OK);
        }
        catch (RuntimeException e)
        {
            Map<String,String>response = new HashMap<>();
            response.put("message","Data Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //Request Body
    @PostMapping("/create")
    ResponseEntity<ToDo> createTodo(@RequestBody ToDo toDo)
    {
        return new ResponseEntity<>(toDoService.createToDo(toDo), HttpStatus.CREATED);

    }
    @PutMapping
    ResponseEntity<ToDo>updateTodo(@RequestBody ToDo toDo)
    {
        return new ResponseEntity<>(toDoService.updateToDo(toDo),HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    void deleteToDoById(@PathVariable Long id)
    {
        toDoService.deletetoDo(id);
    }
}
