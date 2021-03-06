package ToDo.repository;

import ToDo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> , JpaSpecificationExecutor<Task> {
    public List<Task> findAllByIsComplete(Boolean isComplete);
    public List<Task> findAllByTextIgnoreCaseContaining(String text);
    public List<Task> findAllByIsCompleteOrderByCreateDateAsc(Boolean isComplete);
}
