package ru.ngs.summerjob.DemoApp.dao;

import ru.ngs.summerjob.DemoApp.entity.Task;
import ru.ngs.summerjob.DemoApp.exception.TaskNotFoundException;

import java.util.List;

public interface TaskDAO {

    List<Task> getAllTasks();

    Task getTaskById(int id) throws TaskNotFoundException;

    List<Task> getTaskByThemeName(String themeName);

    List<Task> getOverdueTasks();

    void saveTask(Task task);

    void deleteTaskById(int id);
}
