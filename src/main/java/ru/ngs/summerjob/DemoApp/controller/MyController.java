package ru.ngs.summerjob.DemoApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ngs.summerjob.DemoApp.dto.TaskDto;
import ru.ngs.summerjob.DemoApp.facade.DemoAppFacade;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private DemoAppFacade facade;

    //just test
    @RequestMapping("/test")
    public String show1() {
        return "test";
    }

    //выдает список всех задач
    @GetMapping("/getAllTasks")
    public List<TaskDto> showAllTasks() {
//        return taskService.getAllTasks();
        return facade.getAllTaskDto();
    }

    //выдает список просроченных задач
    @GetMapping("/getOverdueTasks")
    public List<TaskDto> getOverdueTasks() {
        return facade.getOverdueTasks();
    }

    //поиск задачи по id
    //если задачи с таким id нет, вернет ответ со статусом 404.
    @GetMapping("/tasks/{id}")
    public TaskDto getTaskById(@PathVariable int id) {
//        return taskService.getTaskById(id);
        return facade.getTaskById(id);
    }

    //получение списка задач по имени темы (имена тем хранятся в отдельной таблице)
    @GetMapping("/tasks")
    public List<TaskDto> getTaskByThemeName(@RequestParam(value = "themeName") String themeName) {
        return facade.getTaskByThemeName(themeName);
    }

    //сохраняет новую задачу и возвращает её с уже присвоенным Id
    //--предполагается что на фронте темы выбираются из списка уже существующих - передается только id темы
    //--время внесения задачи в базу проставляется автоматически
    @PostMapping(value = "/tasks")
        public TaskDto addNewTask(@RequestBody TaskDto taskDto) {
        return facade.saveTask(taskDto);
    }

    //обновляет параметры задачи и возвращает её с изменениями
    @PutMapping("/tasks")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return facade.saveTask(taskDto);
    }

    //позволяет удалить задачу по id,
    //если задачи в базе нет - выбросится исключение.
    @DeleteMapping("/tasks/{id}")
    public String deleteTaskById(@PathVariable int id) {
        facade.deleteTaskById(id);
        return "Task with id:" + id + " was deleted successfully";
    }

    //Предполагается доступ только администратору
    //позволяет удалить тему по id,
    //удаляет все задачи с этой темой.
    @DeleteMapping("/themes/{id}")
    public String deleteThemeById(@PathVariable int id) {
        facade.deleteThemeById(id);
        return "Theme with id:" + id + " was deleted successfully";
    }
}
