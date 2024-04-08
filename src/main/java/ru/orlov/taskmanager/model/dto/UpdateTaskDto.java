package ru.orlov.taskmanager.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class UpdateTaskDto {

    @Size(max = 64)
    private String title;

    @Size(max = 255)
    private String description;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime dueDate;

    private Boolean completed;
}
