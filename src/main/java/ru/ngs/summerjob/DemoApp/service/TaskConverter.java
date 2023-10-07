package ru.ngs.summerjob.DemoApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ngs.summerjob.DemoApp.dto.TaskDto;
import ru.ngs.summerjob.DemoApp.entity.Task;

@Component
public class TaskConverter {

    @Autowired
    private ThemeConverter themeConverter;


    public Task fromTaskDtoToTask(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setImportance(taskDto.getImportance());
        task.setTheme(themeConverter.fromThemeDtoToTheme(taskDto.getTheme()));
        task.setShortName(taskDto.getShortName());
        task.setFullDescription(taskDto.getFullDescription());
        task.setStartTime(taskDto.getStartTime());
        task.setEndTime(taskDto.getEndTime());
        return task;
    }

    public TaskDto fromTaskToTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setImportance(task.getImportance());
        taskDto.setTheme(themeConverter.fromThemeToThemeDto(task.getTheme()));
        taskDto.setShortName(task.getShortName());
        taskDto.setFullDescription(task.getFullDescription());
        taskDto.setStartTime(task.getStartTime());
        taskDto.setEndTime(task.getEndTime());
        return taskDto;
    }
}
