package ru.ngs.summerjob.DemoApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
                .extract().body().jsonPath().getList("theme", Theme.class);

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

        tasks.forEach(task -> Assertions.assertEquals("work", task.getTheme().getName()));
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

    //Проверка добавления новой задачи.
    //проверяет поля получаемые от REST приложения.
    //Если поля отправляемые - проверяет их правильность
    //Если же поля возвращаемые - проверяет не пустые ли они
    //Потом удаляем добавленную тестовую задачу
    @Test
    public void successPostAddNewTask() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationOk200());
        Task sentTask = new Task(5, new Theme(2), "Change it", "Find new job as soon as possible",
                LocalDateTime.of(2024, 1, 1, 0, 0, 1));

        Task answeredTask = given()
                .body(sentTask)
                .when()
                .post("/tasks")
                .then().log().all()
                .extract().as(Task.class);

        Assertions.assertTrue(answeredTask.getId() != 0);
        Assertions.assertEquals(sentTask.getImportance(), answeredTask.getImportance());
        Assertions.assertEquals(sentTask.getTheme().getId(), answeredTask.getTheme().getId());
        Assertions.assertNotNull(answeredTask.getTheme().getName());
        Assertions.assertEquals(sentTask.getShortName(), answeredTask.getShortName());
        Assertions.assertEquals(sentTask.getFullDescription(), answeredTask.getFullDescription());
        Assertions.assertNotNull(answeredTask.getStartTime());
        Assertions.assertEquals(sentTask.getEndTime(), answeredTask.getEndTime());

        given()
                .when()
                .delete("/tasks/" + answeredTask.getId());
    }

    //Проверка как отрабатывает изменение существующего работника
    @Test
    public void successPostUpdateTask() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationOk200());

        //Взяли существующую в базе задачу
        int idTaskForTest = 2;
        Task taskForTest = given()
                .when()
                .get("/tasks/" + idTaskForTest)
                .then().log().all()
                .extract().as(Task.class);

        //сохраняем в переменной изначальное описание задачи хранящейся в базе данных
        String descriptionInDB = taskForTest.getFullDescription();
        //заменяем описание
        taskForTest.setFullDescription("Bla bla bla");

        Task taskAfterUpdate = given()
                .body(taskForTest)
                .when()
                .post("/tasks")
                .then().log().all()
                .extract().as(Task.class);
        //проверяем правильно ли изменилось описание в базе данных
        Assertions.assertEquals("Bla bla bla", taskAfterUpdate.getFullDescription());
        //возвращаем старое поисание
        taskForTest.setFullDescription(descriptionInDB);
        //снова обновляем задание
        Task reUpdated = given()
                .body(taskForTest)
                .when()
                .post("/tasks")
                .then().log().all()
                .extract().as(Task.class);
        //на всякий случай сверяем вернулось ли изначальное описание задачи в нужное поле из базы данных.
        Assertions.assertEquals(descriptionInDB, reUpdated.getFullDescription());
    }

    //Проверка корректности ответа при отправке незаполненного запроса на добавление
    @Test
    public void wrongPostAddNewTask() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationNotFound400());
        Task sentTask = new Task();

        IncorrectTask answeredTask = given()
                .body(sentTask)
                .when()
                .post("/tasks")
                .then().log().all()
                .extract().as(IncorrectTask.class);

        Assertions.assertTrue(answeredTask.getInfo().contains("Cannot invoke"));
    }

    //Проверка правильности отклика при удалении.
    //ПС в тесте сначала создается новая задача - берется её id и происходит удаление по id,
    // чтобы не оставлять "мусор" в базе данных после тестирования.
    @Test
    public void deleteTaskById() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationOk200());
        Task sentTask = new Task(5, new Theme(2), "Change it", "Find new job as soon as possible",
                LocalDateTime.of(2024, 1, 1, 0, 0, 1));
        Task answeredTask = given()
                .body(sentTask)
                .when()
                .post("/tasks")
                .then().log().all()
                .extract().as(Task.class);

        int idTaskForDeleteTest = answeredTask.getId();

        String reply = given()
                .when()
                .delete("/tasks/" + idTaskForDeleteTest)
                .then().extract().asString();

        Assertions.assertEquals("Task with id:" + idTaskForDeleteTest + " was deleted successfully", reply);
    }

    @Test
    public void deleteTaskWithWrongId() {
        Specifications.installSpecification(Specifications.requestSpecification(URL_MAIN), Specifications.responseSpecificationOk200());
        int id = 999999999;
        String reply = given()
                .when()
                .delete("/tasks/" + id)
                .then().extract().asString();

        Assertions.assertEquals("There are no task with id:" + id + " in database", reply);
    }
}
