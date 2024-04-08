package ru.orlov.taskmanager.controller;

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
public class TaskController {

    private static final String FROM = "0";
    private static final String SIZE = "10";

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createNewTask(@RequestBody @Valid NewTaskDto newTaskDto) {
        log.info("POST tasks");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createNewTask(newTaskDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable @Positive Long id,
                                              @RequestBody @Valid UpdateTaskDto updateTaskDto) {
        log.info("PUT tasks/{}", id);
        return ResponseEntity.ok(taskService.updateTask(id, updateTaskDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable @Positive Long id) {
        log.info("GET tasks/{}", id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(@RequestParam(defaultValue = FROM)
                                                     @PositiveOrZero
                                                     Integer from,
                                                     @RequestParam(defaultValue = SIZE)
                                                     @Positive
                                                     Integer size) {
        log.info("GET tasks");
        return ResponseEntity.ok(taskService.getAllTasks(from, size));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable @Positive Long id) {
        log.info("DELETE task/{}", id);
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }
}
