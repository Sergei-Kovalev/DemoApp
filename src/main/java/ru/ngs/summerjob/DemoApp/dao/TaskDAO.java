package ru.ngs.summerjob.DemoApp.dao;

import ru.ngs.summerjob.DemoApp.entity.Task;
import ru.ngs.summerjob.DemoApp.entity.Theme;

import java.util.List;

public interface TaskDAO {

    List<Task> getAllTasks();

    Task getTaskById(int id);

    List<Task> getTaskByThemeName(String themeName);

    void saveTask(Task task);
}
