package ee.pawadeck.taskmanagement.task.repository;

import ee.pawadeck.taskmanagement.error.NotFoundException;
import ee.pawadeck.taskmanagement.task.model.TaskModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskModel, Long> {

    default TaskModel requireById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new NotFoundException("Task ID = '%s' not found", id));
    }

    @Override
    List<TaskModel> findAll();

}
