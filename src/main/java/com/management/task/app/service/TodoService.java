package com.management.task.app.service;

import com.management.task.app.dao.TodoDao;
import com.management.task.app.model.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {

    Logger logger = LoggerFactory.getLogger(TodoService.class);

    @Autowired
    TodoDao todoDao;

    //create todo
    public Todo createTodo(Todo todo){
        todoDao.saveTodo(todo);
        logger.info("CREATED Todo {} ", todo);
        return todo;
    }

    public List<Todo> getAllTodos() {
        return todoDao.getAllTodos();
    }

    public Todo getTodo(int todoId) {
        return todoDao.getTodo(todoId);
    }

    public Todo updateTodo(int todoId, Todo todo){
       return todoDao.updateTodo(todoId, todo);
    }

    public void deleteTodo(int todoId) {
        logger.info("Deleted todo");
        todoDao.deleteTodo(todoId);
    }
}
