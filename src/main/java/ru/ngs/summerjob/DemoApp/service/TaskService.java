package ru.ngs.summerjob.DemoApp.service;

import ru.ngs.summerjob.DemoApp.dto.TaskDto;
import ru.ngs.summerjob.DemoApp.exception.TaskNotFoundException;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks();
    TaskDto getTaskById(int id) throws TaskNotFoundException;
    List<TaskDto> getTaskByThemeName(String themeName);
    List<TaskDto> getOverdueTasks();
    TaskDto saveTask(TaskDto taskDto);
    void deleteTaskById(int id);
}
