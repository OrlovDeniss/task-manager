package ru.orlov.taskmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private Long id;
    private String title;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime dueDate;

    private boolean completed;
}
