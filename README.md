# DemoApp

Проект является REST приложением и протестирован с помощью REST-Assured.
---
___Содержание:___   
* Используемый стек.
* Инструкция по запуску.
* Роли пользователей и эндпоинты приложения.
* Тестирование с помощью REST-Assured.
___     

# Используемый стек.
1. Java 17
2. Maven 
3. Spring Boot 3.0
4. Spring Security 6.0
5. PostgreSQL 14
6. REST-Assured 5.3.0
___

# Инструкция по запуску.
   
* Скачать проект, воспользовавшись функционалом Git.hub либо по [ссылке.](https://github.com/Sergei-Kovalev/DemoApp/archive/refs/heads/master.zip) 
* Настроить базу данных.
* * создать базу данных в PostgreSQL. Изменить настройки подключения к базе данных в файле ___src/main/resources/application.properties___

    PS: по умолчанию:
    
    Название базы данных: _spring.datasource.url=jdbc:postgresql://localhost:5432/for_demo_db_

    Логин для доступа в базу данных: _spring.datasource.username=postgres_

    Пароль: _spring.datasource.password=193233_

* * для создания таблиц в базе данных использовать файлы:

    ___src/main/resources/table_for_db.sql___ (создание основных таблиц) 

    ___src/main/resources/users_role.sql___ (создание ролей пользователей)

* В среде разработки запустить класс ___DemoAppApplication.java___

___

# Роли пользователей и эндпоинты приложения.

 * Для реализации функционала аутентификации и авторизации использовался Spring Security. 
 * Создано 2 роли пользователей - "USER" и "ADMIN". Первый имеет доступ к большинству эндпоинтов, второй - ко всем.
 * Создано 2 пользователя по умолчанию:
  * * ___логин___: Sergey, ___пароль___: Sergey, ___роль___ - "USER"
  * * ___логин___: Georgii, ___пароль___: Georgii, ___роль___ - "ADMIN"
  
        PS: Пароли хранятся в базе данных в зашифрованном виде с использованием хэш-функции bcrypt.
* Создано 2 сущности:
* * Задача (для хранения задач)
* * Тема (для выбора тем задач... Данная сущность создана искусственно для  реализации взаимосвязей между сущностями, разграничения доступа разных пользователей к разным данны и т.п.)

* Эндпроинты:
    1. http://localhost:8080/getAllTasks  : доступ - все роли. GET-запрос.

        Получает JSON со списком всех задач, хранящихся в базе данных.

    2. http://localhost:8080/tasks/{id} : доступ - все роли. GET-запрос.

        Получает JSON с одной задачей по её ___id___ (id число) в базе данных.

        Если id не существует либо некорректное - бросает исключение и выводит сообщение об ошибке.

    3. http://localhost:8080/tasks?themeName={name} : доступ - все роли. GET-запрос.
    
        Получает JSON со списком всех задач с ролью в переменной ___name___. Особое исключение для некорректного ввода не создавалось, предполагается что выбор тем будет реалезован на фронте выбором из списка.

    4. http://localhost:8080/getOverdueTasks : доступ - все роли. GET-запрос.

        Получает JSON со списком просроченных задач.

    5. http://localhost:8080/tasks доступ - все роли. POST-запрос.

        Добавляет задачу, либо изменяет существующую в зависимости от того есть ли она в базе данных. 
        
        Текущее время вносится в базу данных как начало задачи.
        
        Для проверки функционала можно использовать JSON:
        
        Content-Type:application/json

        {"importance": 2,
        "themeType": {
            "id": 2
        },
        "shortName": "invite worker",
        "fullDescription": "Invite Someone else",
        "endTime": "2024-01-01T00:00:00"}

    6. http://localhost:8080/tasks/{id} доступ - все роли. DELETE-запрос.

        Удаляет пользователя по ___id___. Результат метода - текстовое сообщение. 

        Имеет 2 варианта, в зависимости от того, существовала ли задача с таким id в базе данных. (Ответ текстом, скорее для большего разнообразия в тестировании... ответ JSON объектом уже делался выше.)

    7. http://localhost:8080/themes/{id} доступ - только ADMIN. DELETE-запрос.

        Удаляет тему по заданному ___id___, предварительно удалив все темы с таким id. Предполагается что доступ к данному функционалу имеет только администратор.


# Тестирование с помощью REST-Assured.

Для тестирования с помощью REST-Assured, создан тестовый класс RestTest.java, расположенный в проекте по пути src/test/java/ru/ngs/summerjob/DemoApp/

Реализовано 13 тестов для проверки 6 пользовательских эндпоинтов.

PS: Более детальное описание каждого теста расположено в вышеуказанном классе в комментариях.

###### CПАСИБО ЗА ВНИМАНИЕ К МОЕМУ ПРОЕКТУ!!!