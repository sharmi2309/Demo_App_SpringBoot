package com.example.Demo_App.Controller;

import com.example.Demo_App.Models.ToDo;
import com.example.Demo_App.Service.ToDoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/todo")
@Slf4j
public class ToDoController {
    @Autowired
    private ToDoService toDoService;
    @GetMapping("/get")
     ResponseEntity<?> getToDo()
    {
        try {
            List<ToDo> getallTodo = toDoService.getTodo();
            if(getallTodo.size() == 0 )
            {
                Map<String,String>response = new HashMap<>();
                response.put("message","No Records Found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(getallTodo,HttpStatus.OK);
        }
        catch (RuntimeException e)
        {
            log.info("Error");
            Map<String,String>response = new HashMap<>();
            response.put("message","Error Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }
    //PathVariable
    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "ToDo Retrieved SuccessFully"),
            @ApiResponse(responseCode = "404",description = "ToDo Not Found !!")
    })
    ResponseEntity<?> getToDoById(@PathVariable Long id)
    {
        try {
            ToDo getTodo = toDoService.getById(id);
            if(getTodo.getId() == null)
            {
                Map<String,String>response = new HashMap<>();
                response.put("message","No Records Found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(getTodo, HttpStatus.OK);
        }
        catch (RuntimeException e)
        {
            log.warn("Warning Mesaage");
            Map<String,String>response = new HashMap<>();
            response.put("message","Error Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    ResponseEntity <?> getToDoPages(@RequestParam int page , @RequestParam int size)
    {
        try {
            Page<ToDo> getTodo = toDoService.getToDoPages(page, size);
            return new ResponseEntity<>(getTodo, HttpStatus.OK);
        }
        catch (RuntimeException e)
        {
            Map<String,String>response = new HashMap<>();
            response.put("message","Data not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //Request Body
    @PostMapping("/create")
    ResponseEntity<?> createTodo(@Valid @RequestBody ToDo toDo)
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
