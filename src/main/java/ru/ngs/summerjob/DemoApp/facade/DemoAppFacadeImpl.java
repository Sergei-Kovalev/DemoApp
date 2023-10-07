package ru.ngs.summerjob.DemoApp.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ngs.summerjob.DemoApp.dto.TaskDto;
import ru.ngs.summerjob.DemoApp.entity.Task;
import ru.ngs.summerjob.DemoApp.service.TaskConverter;
import ru.ngs.summerjob.DemoApp.service.TaskService;
import ru.ngs.summerjob.DemoApp.service.ThemeService;

import java.util.ArrayList;
import java.util.List;

@Component
public class DemoAppFacadeImpl implements DemoAppFacade {

    @Autowired
    private TaskConverter taskConverter;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ThemeService themeService;

    @Override
    public List<TaskDto> getAllTaskDto() {
        List<TaskDto> taskDtos = new ArrayList<>();
        List<Task> tasks = taskService.getAllTasks();
        for (Task task : tasks) {
            TaskDto taskDto = taskConverter.fromTaskToTaskDto(task);
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }

    @Override
    public TaskDto getTaskById(int id) {
        return taskConverter.fromTaskToTaskDto(taskService.getTaskById(id));
    }

    @Override
    public List<TaskDto> getTaskByThemeName(String themeName) {
        List<TaskDto> taskDtos = new ArrayList<>();
        List<Task> tasks = taskService.getTaskByThemeName(themeName);
        tasks.forEach(task -> taskDtos.add(taskConverter.fromTaskToTaskDto(task)));
        return taskDtos;
    }

    @Override
    public List<TaskDto> getOverdueTasks() {
        List<TaskDto> taskDtos = new ArrayList<>();
        List<Task> overdueTasks = taskService.getOverdueTasks();
        overdueTasks.forEach(task -> taskDtos.add(taskConverter.fromTaskToTaskDto(task)));
        return taskDtos;
    }

    @Override
    public TaskDto saveTask(TaskDto taskDto) {
        Task task = taskService.saveTask(taskConverter.fromTaskDtoToTask(taskDto));
        return taskConverter.fromTaskToTaskDto(task);
    }

    @Override
    public void deleteTaskById(int id) {
        taskService.deleteTaskById(id);
    }

    @Override
    public void deleteThemeById(int id) {
        themeService.deleteThemeById(id);
    }
}
