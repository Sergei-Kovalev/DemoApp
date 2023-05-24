package ru.ngs.summerjob.DemoApp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.ngs.summerjob.DemoApp.entity.Task;
import ru.ngs.summerjob.DemoApp.entity.Theme;
import ru.ngs.summerjob.DemoApp.exception.TaskNotFoundException;

import java.time.LocalDateTime;
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
        Task task = entityManager.find(Task.class, id);
        if (task == null) {
            throw new TaskNotFoundException("Task with id = " + id + " does not exist in the database.");
        } else {
            return task;
        }
    }

    // получение тасков по определенной теме (тема берется по имени выбор из списка на фронте)
    @Override
    public List<Task> getTaskByThemeName(String themeName) {
        Query query1 = entityManager.createQuery("SELECT theme FROM Theme theme WHERE theme.name = :name")
                .setParameter("name", themeName);
        Theme theme = (Theme) query1.getSingleResult();
        Query query = entityManager.createQuery("SELECT t FROM Task t WHERE t.theme = :theme")
                .setParameter("theme", theme);

        return (List<Task>) query.getResultList();
    }

    //получение списка просроченных задач
    @Override
    public List<Task> getOverdueTasks() {
        Query query = entityManager.createQuery("SELECT task FROM Task task WHERE task.endTime < CURRENT_DATE()");
        return (List<Task>) query.getResultList();
    }

    @Override
    public Task saveTask(Task task) {
        int id = task.getTheme().getId();
        Theme theme = entityManager.find(Theme.class, id);
        if (task.getId() == 0) {
            task.setTheme(theme);
            task.setStartTime(LocalDateTime.now());
            entityManager.persist(task);
        } else {
            task.setTheme(theme);
            entityManager.merge(task);
        }
        return task;
    }

    @Override
    public void deleteTaskById(int id) {
        Task task = getTaskById(id);        //чтобы выбросить эксепшн если задачи с таким id нет в базе данных.
        Query query = entityManager.createQuery("DELETE FROM Task WHERE id = :id")
                .setParameter("id", id);
        query.executeUpdate();
    }
}
