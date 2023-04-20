package ru.ngs.summerjob.DemoApp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.ngs.summerjob.DemoApp.entity.Task;

import java.util.List;

@Repository
public class TaskDAOImpl implements TaskDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Task> getAllTasks() {
        Query query = entityManager.createQuery("FROM Task");
        return (List<Task>) query.getResultList();
    }
}
