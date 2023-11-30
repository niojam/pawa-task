package ee.pawadeck.taskmanagement.comment.repository;

import ee.pawadeck.taskmanagement.comment.model.CommentModel;
import ee.pawadeck.taskmanagement.error.NotFoundException;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentModel, Long> {

    default CommentModel requireById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment ID = '%s' not found", id));
    }

    @Override
    List<CommentModel> findAll();

    List<CommentModel> findAllByTaskId(Long taskId);

    @Modifying
    @Query("""
            DELETE FROM comment WHERE comment.task_id = :taskId
           """)
    void deleteAllByTaskId(@Param("taskId") Long taskId);

}
