package ru.orlov.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.orlov.taskmanager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
