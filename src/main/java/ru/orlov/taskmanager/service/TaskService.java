package ru.orlov.taskmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orlov.taskmanager.exception.TaskNotFoundException;
import ru.orlov.taskmanager.model.Task;
import ru.orlov.taskmanager.model.TaskMapper;
import ru.orlov.taskmanager.model.dto.NewTaskDto;
import ru.orlov.taskmanager.model.dto.TaskDto;
import ru.orlov.taskmanager.model.dto.UpdateTaskDto;
import ru.orlov.taskmanager.repository.TaskRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private static final TaskMapper taskMapper = TaskMapper.INSTANCE;
    private final TaskRepository taskRepository;

    public TaskDto createNewTask(NewTaskDto newTaskDto) {
        Task task = taskMapper.toEntity(newTaskDto);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    public TaskDto updateTask(long id, UpdateTaskDto updateTaskDto) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Ошибка при обновлении задачи, задача id = " + id + " не найдена"));
        taskMapper.update(updateTaskDto, task);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    public TaskDto getTaskById(long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Задача id = " + id + " не найдена"));
        return taskMapper.toDto(task);
    }

    public List<TaskDto> getAllTasks(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from/size, size, Sort.by("id"));
        List<Task> tasks = taskRepository.findAll(pageRequest).toList();
        return taskMapper.toDto(tasks);
    }

    public void deleteTaskById(long id) {
        taskRepository.deleteById(id);
    }
}
