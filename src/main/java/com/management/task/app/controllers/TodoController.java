package com.management.task.app.controllers;

import com.management.task.app.exceptions.ResourceNotFoundException;
import com.management.task.app.model.Todo;
import com.management.task.app.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/todos")
public class TodoController {

    Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    TodoService todoService;

    Random random = new Random();

    //create
    @PostMapping
    public ResponseEntity<Todo> createTodoHandler(@RequestBody Todo todo){

        //create todo
        int id = random.nextInt(999999);
        todo.setId(id);
        //create date with system default current date
        Date currentDate = new Date();
        logger.info("current date: {}", currentDate);
        logger.info("todo date: {}", todo.getTodoDate());
        todo.setAddedDate(currentDate);
        logger.info("Create Todo");
        //call service to create todo
        Todo todo1 = todoService.createTodo(todo);
        return new ResponseEntity<>(todo1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodoHandler(){
        return new ResponseEntity<>(todoService.getAllTodos(), HttpStatus.OK);
    }

    //get single todo
    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getSingleTodoHandler(@PathVariable int todoId) throws ResourceNotFoundException {
        return new ResponseEntity<>(todoService.getTodo(todoId), HttpStatus.OK);
    }

    //update todo
    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodoHandler(@RequestBody Todo todoWithNewDetails, @PathVariable int todoId){
        return new ResponseEntity<>(todoService.updateTodo(todoId, todoWithNewDetails), HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable int todoId){
        todoService.deleteTodo(todoId);
        return new ResponseEntity<>("Todo Successfully deleted", HttpStatus.OK);
    }

}
