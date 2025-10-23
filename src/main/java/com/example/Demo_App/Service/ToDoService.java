package com.example.Demo_App.Service;

import com.example.Demo_App.Models.ToDo;
import com.example.Demo_App.Repository.ToDoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Page<ToDo> getToDoPages(int page, int size)
    {
        Pageable pageable = PageRequest.of(page,size);
        return toDoRepository.findAll(pageable);
    }

    public List<ToDo> searchToDos(String title, Boolean isCompleted) {
        return toDoRepository.searchToDos(title, isCompleted);
    }

}
