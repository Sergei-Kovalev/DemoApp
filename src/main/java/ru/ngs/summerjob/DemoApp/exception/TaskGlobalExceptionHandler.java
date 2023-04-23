package ru.ngs.summerjob.DemoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<IncorrectTask> handleException(TaskNotFoundException exception) {
        IncorrectTask incorrectTask = new IncorrectTask();
        incorrectTask.setInfo(exception.getMessage());
        return new ResponseEntity<>(incorrectTask, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectTask> handleException(Exception exception) {
        IncorrectTask incorrectTask = new IncorrectTask();
        incorrectTask.setInfo(exception.getMessage());
        return new ResponseEntity<>(incorrectTask, HttpStatus.BAD_REQUEST);
    }
}
