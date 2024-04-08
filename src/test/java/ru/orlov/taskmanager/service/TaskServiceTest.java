package ru.orlov.taskmanager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.orlov.taskmanager.exception.TaskNotFoundException;
import ru.orlov.taskmanager.model.Task;
import ru.orlov.taskmanager.model.dto.NewTaskDto;
import ru.orlov.taskmanager.model.dto.TaskDto;
import ru.orlov.taskmanager.model.dto.UpdateTaskDto;
import ru.orlov.taskmanager.repository.TaskRepository;
import ru.orlov.taskmanager.utils.TaskTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    private final Random random = new Random();

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createNewTaskTest() {
        NewTaskDto newTaskDto = TaskTestUtils.createNewTaskDto();
        Task task = TaskTestUtils.createTask();

        when(taskRepository.save(any(Task.class)))
                .thenReturn(task);

        TaskDto createdTask = taskService.createNewTask(newTaskDto);

        assertEquals(task.getId(), createdTask.getId());
        assertEquals(task.getTitle(), createdTask.getTitle());
        assertEquals(task.getDescription(), createdTask.getDescription());
        assertEquals(task.getDueDate(), createdTask.getDueDate());
        assertEquals(task.isCompleted(), createdTask.isCompleted());

        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    void updateTaskTest() {
        long id = random.nextLong(10) + 1L;
        UpdateTaskDto updateTaskDto = TaskTestUtils.createUpdateTaskDto();
        Task task = TaskTestUtils.createTask();

        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(task));
        when(taskRepository.save(any(Task.class)))
                .thenReturn(task);

        TaskDto updatedTask = taskService.updateTask(id, updateTaskDto);

        assertEquals(task.getId(), updatedTask.getId());
        assertEquals(task.getTitle(), updatedTask.getTitle());
        assertEquals(task.getDescription(), updatedTask.getDescription());
        assertEquals(task.getDueDate(), updatedTask.getDueDate());
        assertEquals(task.isCompleted(), updatedTask.isCompleted());

        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    void updateTask_whenIdNotFound_thenThrowNotFoundException() {
        long id = random.nextLong(10) + 1L;
        UpdateTaskDto updateTaskDto = TaskTestUtils.createUpdateTaskDto();

        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTask(id, updateTaskDto));

        verify(taskRepository, never())
                .save(any(Task.class));
    }

    @Test
    void getTaskByIdTest() {
        long id = random.nextLong(10) + 1L;
        Task task = TaskTestUtils.createTask();

        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(task));

        TaskDto updatedTask = taskService.getTaskById(id);

        assertEquals(task.getId(), updatedTask.getId());
        assertEquals(task.getTitle(), updatedTask.getTitle());
        assertEquals(task.getDescription(), updatedTask.getDescription());
        assertEquals(task.getDueDate(), updatedTask.getDueDate());
        assertEquals(task.isCompleted(), updatedTask.isCompleted());

        verify(taskRepository, times(1))
                .findById(anyLong());
    }

    @Test
    void getTaskById_whenIdNotFound_thenThrowNotFoundException() {
        long id = random.nextLong(10) + 1L;
        UpdateTaskDto updateTaskDto = TaskTestUtils.createUpdateTaskDto();

        when(taskRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.getTaskById(id));

        verify(taskRepository, times(1))
                .findById(anyLong());
    }

    @Test
    void getAllTasksTest() {
        List<Task> tasks = List.of(TaskTestUtils.createTask());

        when(taskRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(tasks));

        List<TaskDto> foundTasks = taskService.getAllTasks(0, 10);

        Task expected = tasks.stream().findFirst().orElse(null);
        TaskDto actual = foundTasks.stream().findFirst().orElse(null);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDueDate(), actual.getDueDate());
        assertEquals(expected.isCompleted(), actual.isCompleted());

        verify(taskRepository, times(1))
                .findAll(PageRequest.of(0, 10, Sort.by("id")));
    }

    @Test
    void deleteByIdTest() {
        long id = random.nextLong(10) + 1L;
        taskService.deleteTaskById(id);

        verify(taskRepository, times(1))
                .deleteById(id);
    }
}
