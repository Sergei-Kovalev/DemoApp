package ru.ngs.summerjob.DemoApp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.ngs.summerjob.DemoApp.entity.Task;
import ru.ngs.summerjob.DemoApp.entity.Theme;
import ru.ngs.summerjob.DemoApp.exception.TaskNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskDAOImpl implements TaskDAO {

    @Autowired
    private EntityManager entityManager;

    // получение всех тасков
    @Override
    public List<Task> getAllTasks() {
        Query query = entityManager.createQuery("FROM Task");
        return (List<Task>) query.getResultList();
    }

    // получение таска по id
    @Override
    public Task getTaskById(int id) throws TaskNotFoundException {
        return entityManager.find(Task.class, id);
    }

    // получение тасков по определенной теме (тема берется по имени выбор из списка на фронте)
    @Override
    public List<Task> getTaskByThemeName(String themeName) {
        Query query1 = entityManager.createQuery("SELECT theme FROM Theme theme WHERE theme.name = :name")
                .setParameter("name", themeName);
        Theme theme = (Theme) query1.getSingleResult();
        Query query = entityManager.createQuery("SELECT t FROM Task t WHERE t.themeType = :themeType")
                .setParameter("themeType", theme);

        return (List<Task>) query.getResultList();
    }

    //получение списка просроченных задач
    @Override
    public List<Task> getOverdueTasks() {
        List<Task> overdueTasks = new ArrayList<>();
        Query query = entityManager.createQuery("FROM Task");
        List<Task> allTasks = query.getResultList();
        allTasks.forEach(t -> {
            if (t.getEndTime().isBefore(LocalDateTime.now())) {
                overdueTasks.add(t);
            }
        });
        return overdueTasks;
    }

    @Override
    public void saveTask(Task task) {
        int id = task.getThemeType().getId();
        Theme theme = entityManager.find(Theme.class, id);
        if (task.getId() == 0) {
            task.setThemeType(theme);
            task.setStartTime(LocalDateTime.now());
            entityManager.persist(task);
        } else {
            task.setThemeType(theme);
            entityManager.merge(task);
        }
    }

    @Override
    public void deleteTaskById(int id) {
        Query query = entityManager.createQuery("DELETE FROM Task WHERE id = :id")
                .setParameter("id", id);
        query.executeUpdate();
    }

}
