package ToDo.service;

import ToDo.entity.Task;
import ToDo.exception.NotFoundException;
import ToDo.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepo;

    public Task findById(Long id){
        return taskRepo.findById(id)
                .orElseThrow(() ->new NotFoundException(id));
    }
    public List<Task> findAll(){
        return taskRepo.findAll();
    }
    public Task add(Task task){
        task.setCreateDate(new Date());
        task.setUpdateDate(null);
        task.setIsComplete(false);
        return taskRepo.save(task);
    }

    public Task save(Task task, Long id){
        Task saveTask = findById(id);
        if(task.getText() != null){
            saveTask.setText(task.getText());
            saveTask.setUpdateDate(new Date());
        }
        if(task.getIsComplete() != null){
            saveTask.setIsComplete(task.getIsComplete());
            saveTask.setUpdateDate(new Date());
        }
        return taskRepo.save(saveTask);
    }
    public void delete(Long id){
        taskRepo.deleteById(id);
    }

}
