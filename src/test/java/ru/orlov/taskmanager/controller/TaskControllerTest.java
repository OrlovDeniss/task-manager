package ru.orlov.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.orlov.taskmanager.model.dto.NewTaskDto;
import ru.orlov.taskmanager.model.dto.TaskDto;
import ru.orlov.taskmanager.model.dto.UpdateTaskDto;
import ru.orlov.taskmanager.service.TaskService;
import ru.orlov.taskmanager.utils.TaskTestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {

    private static final String REQUEST_MAPPING = "/tasks";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss");

    private final Random random = new Random();

    @MockBean
    private TaskService taskService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void createNewTaskTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2100, 1, 1, 12, 0, 30);
        TaskDto taskDto = TaskTestUtils.createTaskDto();
        NewTaskDto newTaskDto = TaskTestUtils.createNewTaskDto(localDateTime);

        when(taskService.createNewTask(newTaskDto))
                .thenReturn(taskDto);

        mvc.perform(post(REQUEST_MAPPING)
                        .content(objectMapper.writeValueAsString(newTaskDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(taskDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(taskDto.getTitle())))
                .andExpect(jsonPath("$.description", is(taskDto.getDescription())))
                .andExpect(jsonPath("$.dueDate", is(taskDto.getDueDate().format(FORMATTER))))
                .andExpect(jsonPath("$.completed", is(taskDto.isCompleted())));

        verify(taskService, times(1))
                .createNewTask(any(NewTaskDto.class));
    }

    @SneakyThrows
    @Test
    void updateTaskTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2100, 1, 1, 12, 0, 30);
        long id = random.nextLong(10) + 1L;
        TaskDto taskDto = TaskTestUtils.createTaskDto();
        UpdateTaskDto updateTaskDto = TaskTestUtils.createUpdateTaskDto(localDateTime);

        when(taskService.updateTask(anyLong(), eq(updateTaskDto)))
                .thenReturn(taskDto);

        mvc.perform(put(REQUEST_MAPPING + "/{id}", id)
                        .content(objectMapper.writeValueAsString(updateTaskDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(taskDto.getTitle())))
                .andExpect(jsonPath("$.description", is(taskDto.getDescription())))
                .andExpect(jsonPath("$.dueDate", is(taskDto.getDueDate().format(FORMATTER))))
                .andExpect(jsonPath("$.completed", is(taskDto.isCompleted())));

        verify(taskService, times(1))
                .updateTask(anyLong(), any(UpdateTaskDto.class));
    }

    @SneakyThrows
    @Test
    void getTaskByIdTest() {
        long id = random.nextLong(10) + 1L;
        TaskDto taskDto = TaskTestUtils.createTaskDto();

        when(taskService.getTaskById(anyLong()))
                .thenReturn(taskDto);

        mvc.perform(get(REQUEST_MAPPING + "/{id}", id)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(taskDto.getTitle())))
                .andExpect(jsonPath("$.description", is(taskDto.getDescription())))
                .andExpect(jsonPath("$.dueDate", is(taskDto.getDueDate().format(FORMATTER))))
                .andExpect(jsonPath("$.completed", is(taskDto.isCompleted())));

        verify(taskService, times(1))
                .getTaskById(anyLong());
    }

    @SneakyThrows
    @Test
    void getAllTasksTest() {
        TaskDto taskDto = TaskTestUtils.createTaskDto();
        List<TaskDto> tasks = List.of(taskDto);

        when(taskService.getAllTasks(anyInt(), anyInt()))
                .thenReturn(tasks);

        mvc.perform(get(REQUEST_MAPPING)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(tasks.size())));

        verify(taskService, times(1))
                .getAllTasks(anyInt(), anyInt());
    }

    @SneakyThrows
    @Test
    void deleteTaskByIdTest() {
        long id = random.nextLong(10) + 1L;

        mvc.perform(delete(REQUEST_MAPPING + "/{id}", id)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(taskService, times(1))
                .deleteTaskById(anyLong());
    }
}
