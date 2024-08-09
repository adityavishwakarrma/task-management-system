package com.management.task.app.dao;

import com.management.task.app.exceptions.ResourceNotFoundException;
import com.management.task.app.helper.Helper;
import com.management.task.app.model.Todo;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Data
public class TodoDao {

    Logger logger = LoggerFactory.getLogger(TodoDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public TodoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        //create table if does not exists

        String createTable = "create table IF NOT EXISTS todos(id int primary key, title varchar(100) not null, content varchar(4500), status varchar(10) not null, addedDate datetime, todoDate datetime)";
        int update = jdbcTemplate.update(createTable);
        logger.info("TODOS TABLE CREATED", update);
    }



    //save todo in database
    public Todo saveTodo(Todo todo) {
        String insertQuery = " insert into todos(id,title,content,status,addedDate,todoDate)" + "values(?,?,?,?,?,?)";
        int rows = jdbcTemplate.update(insertQuery, todo.getId(), todo.getTitle(), todo.getContent(), todo.getStatus(), todo.getAddedDate(), todo.getTodoDate());
        logger.info("JDBC OPERATION: {} rows inserted", rows);
        return todo;
    }

    //get single todo from datasource
    public Todo getTodo(int id) {
        try
        {
            String query = "select * from todos WHERE id=?";

            Todo todo = jdbcTemplate.queryForObject(query, new TodoRowMapper(), id);
            logger.info("TODO : {}", todo);
            return todo;
        }
        catch (Exception e){
            throw new ResourceNotFoundException("user not found with given id", HttpStatus.OK);
        }
    }

    //get all todo from database
    public List<Todo> getAllTodos() {
        String query = "select * from todos";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(query);
        logger.info("maps : {}", maps);

        List<Todo> collect = maps.stream().map(todoData -> {
            Todo todo = new Todo();
            todo.setId((int) todoData.get("id"));
            todo.setTitle((String) todoData.get("title"));
            todo.setContent((String) todoData.get("content"));
            todo.setStatus((String) todoData.get("status"));
            try {
                todo.setAddedDate(Helper.parseDate((LocalDateTime) todoData.get("addedDate")));
                todo.setTodoDate(Helper.parseDate((LocalDateTime) todoData.get("todoDate")));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            return todo;
        }).collect(Collectors.toList());

        return collect;
    }

    //update todo
    public Todo updateTodo(int id, Todo newTodo){
        String query="update todos set title=?, content=?, status=?,addedDate=?, todoDate=?" +
                "WHERE id=?";
        int update = jdbcTemplate.update(query, newTodo.getTitle(), newTodo.getContent(), newTodo.getStatus(), newTodo.getAddedDate(), newTodo.getTodoDate(), id);
        logger.info("UPDATED {} rows", update);

        newTodo.setId(id);
        return newTodo;
    }

    //delete todo from datasource
    public void deleteTodo(int id){
        String query="delete from todos WHERE id=?";
        int rows = jdbcTemplate.update(query, id);
        logger.info("DELETE {} rows", rows);
    }

    public void deleteMultipleTodos(int[] ids){
        String query="select from todos WHERE id=?";
        //for, forEach=>
        int[] ints = jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int id = ids[i];
                ps.setInt(1, id);
            }

            @Override
            public int getBatchSize() {
                return ids.length;
            }
        });

        for (int i : ints) {
            logger.info("DELETED : {}", i);
        }

    }

}
