package ru.ngs.summerjob.DemoApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ngs.summerjob.DemoApp.entity.Task;
import ru.ngs.summerjob.DemoApp.service.TaskService;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/test")
    public String show1() {
        return "test";
    }

    @RequestMapping("/getAllTasks")
    public List<Task> showAllTasks() {
        return taskService.getAllTasks();
    }

}
