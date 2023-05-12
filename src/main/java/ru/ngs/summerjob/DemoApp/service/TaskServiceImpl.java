package ru.ngs.summerjob.DemoApp.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ngs.summerjob.DemoApp.dao.TaskDAO;
import ru.ngs.summerjob.DemoApp.dto.TaskDto;
import ru.ngs.summerjob.DemoApp.entity.Task;
import ru.ngs.summerjob.DemoApp.exception.TaskNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private TaskConverter taskConverter;

    @Override
    @Transactional
    public List<TaskDto> getAllTasks() {
        List<TaskDto> tasksDto = new ArrayList<>();
        List<Task> allTasks = taskDAO.getAllTasks();
        for (Task task : allTasks) {
            TaskDto taskDto = taskConverter.fromTaskToTaskDto(task);
            tasksDto.add(taskDto);
        }
        return tasksDto;
    }

    @Override
    @Transactional
    public TaskDto getTaskById(int id) throws TaskNotFoundException {
        return taskConverter.fromTaskToTaskDto(taskDAO.getTaskById(id));
    }

    @Override
    @Transactional
    public List<TaskDto> getTaskByThemeName(String themeName) {
        List<TaskDto> tasksDto = new ArrayList<>();
        List<Task> tasks = taskDAO.getTaskByThemeName(themeName);
        for (Task task : tasks) {
            tasksDto.add(taskConverter.fromTaskToTaskDto(task));
        }
        return tasksDto;
    }

    @Override
    @Transactional
    public List<TaskDto> getOverdueTasks() {
        List<TaskDto> tasksDto = new ArrayList<>();
        List<Task> tasks = taskDAO.getOverdueTasks();
        tasks.forEach(task -> tasksDto.add(taskConverter.fromTaskToTaskDto(task)));
        return tasksDto;
    }

    @Override
    @Transactional
    public TaskDto saveTask(TaskDto taskDto) {
        Task task = taskDAO.saveTask(taskConverter.fromTaskDtoToTask(taskDto));
        return taskConverter.fromTaskToTaskDto(task);
    }

    @Override
    @Transactional
    public void deleteTaskById(int id) {
        taskDAO.deleteTaskById(id);
    }


}
