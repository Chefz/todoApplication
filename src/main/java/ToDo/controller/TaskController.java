package ToDo.controller;

import ToDo.entity.Task;
import ToDo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/task")
    public List<Task> findAll(){
        return taskService.findAll();
    }
    @GetMapping("/task/{id}")
    public Task findById(@PathVariable Long id){
        return taskService.findById(id);
    }
    @PostMapping("/task")
    public Task add(@RequestBody Task task ){
        return taskService.add(task);
    }
    //@RequestMapping(method = RequestMethod.PUT, produces = "application/json", value = "/task/{id}")
    @PutMapping("/task/{id}")
    public  Task save (@RequestBody Task task, @PathVariable Long id){
        return taskService.save(task,id);
    }
    @DeleteMapping("/task/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }

}
