package com.management.task.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Todo {
    private int id;
    private String title;
    private String description;
    private String status;
    private Date addedDate;
    //    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date todoDate;
}