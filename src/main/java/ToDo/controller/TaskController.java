package ToDo.controller;

import ToDo.DTO.FindParams;
import ToDo.entity.Task;
import ToDo.exception.NotFoundException;
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
        return clear(taskService.findAll());
    }
    private List<Task> clear(List<Task> tasks){

        tasks.forEach(this::clear);
        return tasks;
    }
    private Task clear(Task task){
        if(task.getTags()!= null) {
            task.getTags().forEach(tag -> {
                tag.setTasks(null);
            });
        }
        return task;
    }
    @GetMapping("/task/{id}")
    public Task findById(@PathVariable Long id){

        return clear(taskService.findById(id));
    }
    @PostMapping("/task/{taskId}/addTag/{tagId}")
    public Task addTag(@PathVariable Long taskId, @PathVariable  Long tagId){
        return clear(taskService.addTag(taskId,tagId));
    }
    @PostMapping("/task")
    public Task add(@RequestBody Task task ){
        return clear(taskService.add(task));
    }

    @PutMapping("/task/{id}/editText/{text}")
    public Task editText(@PathVariable Long id, @PathVariable String text){ //Изменение текста
        return clear(taskService.editText(text,id));
    }
    @PutMapping("/task/{id}/status/{status}")
    public Task setIsComplete(@PathVariable Long id,@PathVariable Boolean status){ //Установка завершенности задачи
        return clear(taskService.setComplete(id, status));
    }

    @DeleteMapping("/task/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }

    @DeleteMapping("/task")
    public void deleteAll(){
        taskService.deleteAll();
    }
    @PostMapping("/task/findByParams")
    public List<Task> findByParams(@RequestBody FindParams findParams){
        return clear(taskService.findByParam(findParams));
    }

}
