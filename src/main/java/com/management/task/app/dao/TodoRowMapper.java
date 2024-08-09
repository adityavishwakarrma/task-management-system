package com.management.task.app.dao;


import com.management.task.app.helper.Helper;
import com.management.task.app.model.Todo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;

public class TodoRowMapper implements RowMapper<Todo> {
    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Todo todo = new Todo();
        todo.setId( rs.getInt("id"));
        todo.setTitle( rs.getString("title"));
        todo.setContent( rs.getString("content"));
        todo.setStatus( rs.getString("status"));
        try {
            todo.setAddedDate(Helper.parseDate((LocalDateTime) rs.getObject("addedDate")));
            todo.setTodoDate(Helper.parseDate((LocalDateTime) rs.getObject("todoDate")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return todo;
    }
}
