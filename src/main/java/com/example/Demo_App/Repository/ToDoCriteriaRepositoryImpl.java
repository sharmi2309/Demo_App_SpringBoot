package com.example.Demo_App.Repository;

import com.example.Demo_App.Models.ToDo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ToDoCriteriaRepositoryImpl implements ToDoCriteriaRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ToDo> searchToDos(String title, Boolean is_Completed)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ToDo> cq = cb.createQuery(ToDo.class);
        Root<ToDo> root = cq.from(ToDo.class);
        List<Predicate> pred = new ArrayList<>();
        if(title !=null && !title.isEmpty())
        {
            pred.add(cb.like(root.get("title"),"%" +title+ "%"));

        }
        if(is_Completed !=null)
        {
            pred.add(cb.equal(root.get("is_Completed"),is_Completed));
        }
        cq.where(pred.toArray(new Predicate[0]));
        TypedQuery<ToDo> query = entityManager.createQuery(cq);
        return query.getResultList();

    }
}
