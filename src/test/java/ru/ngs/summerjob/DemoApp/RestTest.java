package ru.ngs.summerjob.DemoApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.ngs.summerjob.DemoApp.entity.Task;
import ru.ngs.summerjob.DemoApp.entity.Theme;
import ru.ngs.summerjob.DemoApp.exception.IncorrectTask;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;

public class RestTest {
    private final static String URL_MAIN = "http://localhost:8080";

    //Проверка, что в списке по запросу /getAllTasks Важность задачи не выше 5 (согласно условию ТЗ).
    @Test
    public void checkTaskImportanceMaxValue() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationOk200());
        List<Task> tasks = given()
                .when()
                .get("/getAllTasks")
                .then().log().all()
                .extract().body().jsonPath().getList(".", Task.class);

        tasks.forEach(task -> Assertions.assertTrue(task.getImportance() <= 5));
    }

    //Проверка, что у первой задачи в списке тема равна "home".
    @Test
    public void checkThemeValue() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationOk200());
        List<Theme> themes = given()
                .when()
                .get("/getAllTasks")
                .then().log().all()
                .extract().body().jsonPath().getList("themeType", Theme.class);

        Assertions.assertEquals("home", themes.get(0).getName());
    }

    //Поверка, что в списке по запросу /getAllTasks время создания задач более позднее, чем сейчас.
    @Test
    public void checkTaskStartTime() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationOk200());
        List<Task> tasks = given()
                .when()
                .get("/getAllTasks")
                .then().log().all()
                .extract().body().jsonPath().getList(".", Task.class);

        tasks.forEach(task -> Assertions.assertTrue(task.getStartTime().isBefore(LocalDateTime.now())));
    }

    //Проверка, что по запросу /tasks/{id} получаем ответ с задачей с id = {id}
    @Test
    public void checkRequestedTaskById() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationOk200());
        int id = 2;
        Task task = given()
                .when()
                .get("/tasks/" + id)
                .then().log().all()
                .extract().body().as(Task.class);

        Assertions.assertEquals(task.getId(), id);
    }

    //Проверка, что по запросу /tasks/{id} получаем ответ с задачей с id = {id}
    // где {id} не существует, возвращается ответ в виде класса IncorrectTask.java
    // и код ошибки 404
    // + проверяется корректность сообщения об ошибке
    @Test
    public void checkIncorrectTaskId() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationNotFound404());
        int id = 55;
        IncorrectTask incorrectTask = given()
                .when()
                .get("/tasks/" + id)
                .then().log().all()
                .extract().body().as(IncorrectTask.class);

        Assertions.assertInstanceOf(IncorrectTask.class, incorrectTask);
        Assertions.assertEquals("Task with id = " + id + " does not exist in the database.", incorrectTask.getInfo());
    }

    //Проверка, что по запросу /tasks?themeName=name находит задачи с этой темой
    @Test
    public void checkTasksWithThemeName() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationOk200());
        String name = "work";
        List<Task> tasks = given()
                .when()
                .get("/tasks?themeName=" + name)
                .then().log().all()
                .extract().body().jsonPath().getList(".", Task.class);

        tasks.forEach(task -> Assertions.assertEquals("work", task.getThemeType().getName()));
    }

    //Проверка, что по запросу /tasks?themeName=name при неверное теме падает с ошибкой 400
    //ПС: Исключение для данной ситуации не создавалось.. продемонтрировать разные подходы
    // + предположение что на фронте реализован выбор из списка.
    @Test
    public void checkTasksWithIncorrectThemeName() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationNotFound400());
        String name = "wor";
        IncorrectTask incorrectTask = given()
                .when()
                .get("/tasks?themeName=" + name)
                .then().log().all()
                .extract().body().as(IncorrectTask.class);

        Assertions.assertTrue(incorrectTask.getInfo().contains("No result found for query"));
    }


    //Поверка, что в список по запросу /getOverdueTasks попадут просроченные задачи.
    @Test
    public void checkOverdueTask() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationOk200());
        List<Task> tasks = given()
                .when()
                .get("/getOverdueTasks")
                .then().log().all()
                .extract().body().jsonPath().getList(".", Task.class);

        tasks.forEach(task -> Assertions.assertTrue(task.getEndTime().isBefore(LocalDateTime.now())));
    }
}
