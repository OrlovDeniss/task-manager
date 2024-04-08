package ru.orlov.taskmanager.utils;

import lombok.experimental.UtilityClass;
import org.jeasy.random.EasyRandom;
import ru.orlov.taskmanager.model.Task;
import ru.orlov.taskmanager.model.dto.NewTaskDto;
import ru.orlov.taskmanager.model.dto.TaskDto;
import ru.orlov.taskmanager.model.dto.UpdateTaskDto;

import java.time.LocalDateTime;

@UtilityClass
public class TaskTestUtils {

    private final EasyRandom easyRandom = new EasyRandom();

    public Task createTask() {
        return easyRandom.nextObject(Task.class);
    }

    public NewTaskDto createNewTaskDto() {
        return easyRandom.nextObject(NewTaskDto.class);
    }

    public NewTaskDto createNewTaskDto(LocalDateTime localDateTime) {
        NewTaskDto newTaskDto = easyRandom.nextObject(NewTaskDto.class);
        newTaskDto.setDueDate(localDateTime);
        return newTaskDto;
    }

    public UpdateTaskDto createUpdateTaskDto() {
        return easyRandom.nextObject(UpdateTaskDto.class);
    }

    public UpdateTaskDto createUpdateTaskDto(LocalDateTime localDateTime) {
        UpdateTaskDto updateTaskDto = easyRandom.nextObject(UpdateTaskDto.class);
        updateTaskDto.setDueDate(localDateTime);
        return updateTaskDto;
    }

    public TaskDto createTaskDto() {
        return easyRandom.nextObject(TaskDto.class);
    }
}
