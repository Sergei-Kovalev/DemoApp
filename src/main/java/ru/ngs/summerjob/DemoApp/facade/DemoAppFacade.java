package ru.ngs.summerjob.DemoApp.facade;

import ru.ngs.summerjob.DemoApp.dto.TaskDto;

import java.util.List;

public interface DemoAppFacade {
    List<TaskDto> getAllTaskDto();
    TaskDto getTaskById(int id);
    List<TaskDto> getTaskByThemeName(String themeName);
    List<TaskDto> getOverdueTasks();
    TaskDto saveTask(TaskDto taskDto);
    void deleteTaskById(int id);
    void deleteThemeById(int id);
}
