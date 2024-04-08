package ru.orlov.taskmanager.model;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.orlov.taskmanager.model.dto.NewTaskDto;
import ru.orlov.taskmanager.model.dto.TaskDto;
import ru.orlov.taskmanager.model.dto.UpdateTaskDto;

import java.util.List;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task toEntity(NewTaskDto newTaskDto);

    TaskDto toDto(Task task);

    List<TaskDto> toDto(List<Task> user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(UpdateTaskDto updateTaskDto, @MappingTarget Task task);
}
