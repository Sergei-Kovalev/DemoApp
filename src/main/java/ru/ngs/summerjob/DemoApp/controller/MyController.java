package ru.ngs.summerjob.DemoApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ngs.summerjob.DemoApp.entity.Task;
import ru.ngs.summerjob.DemoApp.entity.Theme;
import ru.ngs.summerjob.DemoApp.service.TaskService;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private TaskService taskService;

    //just test
    @RequestMapping("/test")
    public String show1() {
        return "test";
    }

    //выдает список всех задач
    @GetMapping("/getAllTasks")
    public List<Task> showAllTasks() {
        return taskService.getAllTasks();
    }

    //поиск задачи по id
    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    //получение списка задач по имени темы (имена тем хранятся в отдельной таблице)
    @GetMapping("/tasks")
    public List<Task> getTaskByThemeName(@RequestParam(value = "themeName") String themeName) {
        return taskService.getTaskByThemeName(themeName);
    }

    //сохраняет новую задачу и возвращает её с уже присвоенным Id
    //--предполагается что на фронте темы выбираются из списка уже существующих - передается только id темы
    //--время внесения задачи в базу проставляется автоматически
    @PostMapping(value = "/tasks")
        public Task addNewTask(@RequestBody Task task) {
        taskService.saveTask(task);
        return task;
    }

    //обновляет параметры задачи и возвращает её с изменениями
    @PutMapping("/tasks")
    public Task updateTask(@RequestBody Task task) {
        taskService.saveTask(task);
        return task;
    }
}
