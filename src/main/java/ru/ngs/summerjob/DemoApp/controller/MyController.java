package ru.ngs.summerjob.DemoApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ngs.summerjob.DemoApp.entity.Task;
import ru.ngs.summerjob.DemoApp.exception.TaskNotFoundException;
import ru.ngs.summerjob.DemoApp.service.TaskService;
import ru.ngs.summerjob.DemoApp.service.ThemeService;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ThemeService themeService;

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

    //выдает список просроченных задач
    @GetMapping("/getOverdueTasks")
    public List<Task> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }

    //поиск задачи по id
    //если задачи с таким id нет, вернет ответ со статусом 404.
    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable int id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            throw new TaskNotFoundException("Task with id = " + id + " does not exist in the database.");
        }
        return task;
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

    //позволяет удалить задачу по id,
    //если задачи в базе нет - пишет сообщение что её нет.
    //здесь ошибка не обрабатывается - так как результат метода = сообщение.
    @DeleteMapping("/tasks/{id}")
    public String deleteTaskById(@PathVariable int id) throws TaskNotFoundException {
        Task taskById = taskService.getTaskById(id);
        if (taskById == null) {
            return "There are no task with id:" + id + " in database";
        } else {
            taskService.deleteTaskById(id);
            return "Task with id:" + id + " was deleted successfully";
        }
    }

    //Предполагается доступ только администратору
    //позволяет удалить тему по id,
    //удаляет все задачи с этой темой.
    @DeleteMapping("/themes/{id}")
    public String deleteThemeById(@PathVariable int id) {
        themeService.deleteThemeById(id);
        return "Theme with id:" + id + " was deleted successfully";
    }

}
