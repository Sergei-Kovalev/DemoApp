package ru.ngs.summerjob.DemoApp.service;

import ru.ngs.summerjob.DemoApp.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(int id);
    List<Task> getTaskByThemeName(String themeName);
    void saveTask(Task task);
}
