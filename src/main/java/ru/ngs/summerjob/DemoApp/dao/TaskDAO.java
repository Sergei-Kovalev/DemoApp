package ru.ngs.summerjob.DemoApp.dao;

import ru.ngs.summerjob.DemoApp.entity.Task;

import java.util.List;

public interface TaskDAO {

    public List<Task> getAllTasks();
}
