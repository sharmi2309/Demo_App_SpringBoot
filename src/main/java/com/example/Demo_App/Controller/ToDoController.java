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

@CrossOrigin("http://localhost:4200")
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
                response.put("error_message","No ToDo records found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(getallTodo,HttpStatus.OK);
        }
        catch (RuntimeException e)
        {
            log.info("Error");
            Map<String,String>response = new HashMap<>();
            response.put("error_message","An error occurred while retrieving ToDos.");
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
                response.put("error_message","ToDo not found for the given ID.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(getTodo, HttpStatus.OK);
        }
        catch (RuntimeException e)
        {
            log.warn("Warning Mesaage");
            Map<String,String>response = new HashMap<>();
            response.put("error_message","An internal error occurred while fetching the ToDo.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<?> getToDoPages(@RequestParam int page, @RequestParam int size) {
        try {
            Page<ToDo> todos = toDoService.getToDoPages(page, size);

            if (todos.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error_message", "No ToDo records found for the given page."));
            }

            return ResponseEntity.ok(todos);

        } catch (RuntimeException e) {
            log.error("Error fetching paginated ToDos", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error_message", "Failed to fetch ToDo data."));
        }
    }

    //Request Body
    @PostMapping("/create")
    ResponseEntity<?> createTodo(@Valid @RequestBody ToDo toDo)
    {
        try {
            ToDo created = toDoService.createToDo(toDo);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("message", "ToDo created successfully!", "data", created));

        } catch (RuntimeException e) {
            log.error("Error creating ToDo", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error_message", "Failed to create ToDo."));
        }
    }
    @PutMapping
    ResponseEntity<?>updateTodo(@RequestBody ToDo toDo)
    {
        try {
            ToDo updated = toDoService.updateToDo(toDo);
            return ResponseEntity
                    .ok(Map.of("message", "ToDo updated successfully!", "data", updated));

        } catch (RuntimeException e) {
            log.error("Error updating ToDo", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error_message", "Failed to update ToDo."));
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteToDoById(@PathVariable Long id)
    {
        try {
            toDoService.deletetoDo(id);
            return ResponseEntity.ok(Map.of("message", "ToDo deleted successfully!"));
        } catch (RuntimeException e) {
            log.error("Error deleting ToDo with ID: {}", id, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error_message", "Failed to delete ToDo."));
        }
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchToDos(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean isCompleted) {
        try {
            List<ToDo> results = toDoService.searchToDos(title, isCompleted);

            if (results.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error_message", "No matching ToDo records found."));
            }

            return ResponseEntity.ok(results);

        } catch (RuntimeException e) {
            log.error("Error searching ToDos", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error_message", "Error occurred during ToDo search."));
        }
    }
}
