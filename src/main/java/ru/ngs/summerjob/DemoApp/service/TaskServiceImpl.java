package ru.ngs.summerjob.DemoApp.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ngs.summerjob.DemoApp.dao.TaskDAO;
import ru.ngs.summerjob.DemoApp.entity.Task;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO taskDAO;

    @Override
    @Transactional
    public List<Task> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    @Override
    @Transactional
    public Task getTaskById(int id) {
        return taskDAO.getTaskById(id);
    }

    @Override
    @Transactional
    public List<Task> getTaskByThemeName(String themeName) {
        return taskDAO.getTaskByThemeName(themeName);
    }

    @Override
    @Transactional
    public void saveTask(Task task) {
        taskDAO.saveTask(task);
    }


}
