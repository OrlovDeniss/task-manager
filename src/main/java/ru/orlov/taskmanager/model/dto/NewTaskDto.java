package ru.orlov.taskmanager.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class NewTaskDto {

    @NotBlank
    @Size(max = 64)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String description;

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    @Schema(pattern = "yyyy-MM-dd'T'hh:mm:ss", example = "2030-01-01'T'12:00:00")
    private LocalDateTime dueDate;
}
