package ru.orlov.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.orlov.taskmanager.model.dto.NewTaskDto;
import ru.orlov.taskmanager.model.dto.TaskDto;
import ru.orlov.taskmanager.model.dto.UpdateTaskDto;
import ru.orlov.taskmanager.service.TaskService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
@Tag(name = "Задачи", description = "API управления задачами")
public class TaskController {

    private static final String FROM = "0";
    private static final String SIZE = "10";

    private final TaskService taskService;

    @Operation(summary = "Создать новую задачу")
    @ApiResponse(responseCode = "200", description = "Создание новой задачи",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))})
    @PostMapping
    public ResponseEntity<TaskDto> createNewTask(@RequestBody @Valid NewTaskDto newTaskDto) {
        log.info("POST tasks");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createNewTask(newTaskDto));
    }

    @Operation(summary = "Обновить задачу по id")
    @ApiResponse(responseCode = "200", description = "Обновление задачи по id",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))})
    @PutMapping("{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable @Positive Long id,
                                              @RequestBody @Valid UpdateTaskDto updateTaskDto) {
        log.info("PUT tasks/{}", id);
        return ResponseEntity.ok(taskService.updateTask(id, updateTaskDto));
    }

    @Operation(summary = "Получить задачу по id")
    @ApiResponse(responseCode = "200", description = "Получение существующей задачи по id",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))})
    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable @Positive Long id) {
        log.info("GET tasks/{}", id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(summary = "Получить все задачи")
    @ApiResponse(responseCode = "200", description = "Получение всех задач",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TaskDto.class))))
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(@RequestParam(defaultValue = FROM) @PositiveOrZero Integer from,
                                                     @RequestParam(defaultValue = SIZE) @Positive Integer size) {
        log.info("GET tasks");
        return ResponseEntity.ok(taskService.getAllTasks(from, size));
    }

    @Operation(summary = "Удалить задачу по id")
    @ApiResponse(responseCode = "200", description = "Удаление задачи по id",
            content = {@Content(mediaType = "application/json")})
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable @Positive Long id) {
        log.info("DELETE task/{}", id);
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }
}
