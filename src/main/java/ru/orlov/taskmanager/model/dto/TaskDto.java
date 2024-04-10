package ru.orlov.taskmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private Long id;
    private String title;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    @Schema(pattern = "yyyy-MM-dd'T'hh:mm:ss", example = "2030-01-01'T'12:00:00")
    private LocalDateTime dueDate;

    private boolean completed;
}
