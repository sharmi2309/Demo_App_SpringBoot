package com.example.Demo_App;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/todo")
public class ToDoController {
    @GetMapping("/get")
    String getToDo()
    {
        return "ToDo List";
    }
    //PathVariable
    @GetMapping("/{id}")
    String getToDoById(@PathVariable int id)
    {
        return "ToDo List with Id " +id;
    }
    //RequestParam
    @GetMapping("")
    String getToDoByIdParam(@RequestParam("todoId") int id)
    {
        return "ToDo List with Id " +id;
    }
    //Request Body
    @PostMapping("/create")
    String createUser(@RequestBody String body)
    {
        return body;

    }
}
