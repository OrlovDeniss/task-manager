package ru.orlov.taskmanager.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.orlov.taskmanager.model.Task;
import ru.orlov.taskmanager.model.dto.NewTaskDto;
import ru.orlov.taskmanager.model.dto.TaskDto;
import ru.orlov.taskmanager.model.dto.UpdateTaskDto;
import ru.orlov.taskmanager.repository.TaskRepository;
import ru.orlov.taskmanager.service.TaskService;
import ru.orlov.taskmanager.utils.TaskTestUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TaskManagerIntegrationTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss");

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Test
    void createNewTaskTest() {
        NewTaskDto newTaskDto = TaskTestUtils.createNewTaskDto();
        TaskDto createdTaskDto = taskService.createNewTask(newTaskDto);

        assertNotNull(createdTaskDto.getId());
        assertEquals(newTaskDto.getTitle(), createdTaskDto.getTitle());
        assertEquals(newTaskDto.getDescription(), createdTaskDto.getDescription());
        assertEquals(newTaskDto.getDueDate().format(FORMATTER), createdTaskDto.getDueDate().format(FORMATTER));
        assertFalse(createdTaskDto.isCompleted());
    }

    @Test
    void updateTaskTest() {
        Task createdTask = taskRepository.save(TaskTestUtils.createTask());
        long id = createdTask.getId();

        UpdateTaskDto updateTaskDto = TaskTestUtils.createUpdateTaskDto();
        TaskDto updatedTaskDto = taskService.updateTask(id, updateTaskDto);

        assertEquals(id, updatedTaskDto.getId());
        assertEquals(updateTaskDto.getTitle(), updatedTaskDto.getTitle());
        assertEquals(updateTaskDto.getDescription(), updatedTaskDto.getDescription());
        assertEquals(updateTaskDto.getDueDate().format(FORMATTER), updatedTaskDto.getDueDate().format(FORMATTER));
        assertEquals(updateTaskDto.getCompleted(), updatedTaskDto.isCompleted());
    }

    @Test
    void findByIDTest() {
        Task createdTask = taskRepository.save(TaskTestUtils.createTask());
        long id = createdTask.getId();

        TaskDto taskDto = taskService.getTaskById(id);

        assertEquals(id, taskDto.getId());
        assertEquals(createdTask.getTitle(), taskDto.getTitle());
        assertEquals(createdTask.getDescription(), taskDto.getDescription());
        assertEquals(createdTask.getDueDate().format(FORMATTER), taskDto.getDueDate().format(FORMATTER));
        assertEquals(createdTask.isCompleted(), taskDto.isCompleted());
    }

    @Test
    void findAllTest() {
        Task createdTask = taskRepository.save(TaskTestUtils.createTask());

        List<TaskDto> tasks = taskService.getAllTasks(0, 10);
        TaskDto taskDto = tasks.stream().findFirst().orElse(null);

        assertEquals(createdTask.getId(), taskDto.getId());
        assertEquals(createdTask.getTitle(), taskDto.getTitle());
        assertEquals(createdTask.getDescription(), taskDto.getDescription());
        assertEquals(createdTask.getDueDate().format(FORMATTER), taskDto.getDueDate().format(FORMATTER));
        assertEquals(createdTask.isCompleted(), taskDto.isCompleted());
    }

    @Test
    void deleteByIdTest() {
        Task createdTask = taskRepository.save(TaskTestUtils.createTask());
        long id = createdTask.getId();

        taskService.deleteTaskById(id);
        List<TaskDto> tasks = taskService.getAllTasks(0, 10);

        assertEquals(0, tasks.size());
    }
}
