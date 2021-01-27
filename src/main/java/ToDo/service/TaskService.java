package ToDo.service;

import ToDo.DTO.FindParams;
import ToDo.entity.Tag;
import ToDo.entity.Task;
import ToDo.exception.AlreadyCompletedException;
import ToDo.exception.NotFoundException;
import ToDo.exception.NotFoundParentException;
import ToDo.exception.NotFoundTagException;
import ToDo.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepo;
    private final TagService tagService;

    public Task findById(Long id){
        Task task =  taskRepo.findById(id)
                .orElseThrow(() ->new NotFoundException(id));
        return task;
    }
    public List<Task> findAll(){
        return taskRepo.findAll();
    }
    public Task add(Task task){
        //Изменения
            task.setCreateDate(new Date());
            task.setUpdateDate(null);
            task.setIsComplete(false);
            return taskRepo.save(task);
    }

    public Task editText(String text, Long id){//Изменения
        Task task = findById(id);
        if(text!= null){
            task.setText(text);
            task.setUpdateDate(new Date());
        }
        return taskRepo.save(task);
    }
    public Task setComplete(Long id, Boolean status){  //Изменения
        Task task = findById(id);
        if(task.getIsComplete()){
            throw new AlreadyCompletedException(id);
        }
        task.setIsComplete(status);
        task.setUpdateDate(new Date());
        return taskRepo.save(task);
    }

    public void delete(Long id){
        Task task = findById(id);
        taskRepo.deleteById(id);
    }
    public Task addTag(Long taskId, Long tagId){
        Tag tag = tagService.findById(tagId);
        Task task  = findById(taskId);
        if(task.getTags() == null) {
            task.setTags(new HashSet<>());
        }
        task.getTags().add(tag);
        return taskRepo.save(task);
    }
    public List<Task> findByParam(FindParams findParams){
        return taskRepo.findAll((root, criteriaQuery, criteriaBuilder) -> {
           criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
           List<Predicate>  predicates= new ArrayList<>();
           if(findParams.getIsComplete() !=null){
               predicates.add(criteriaBuilder.equal(root.get("isComplete"),findParams.getIsComplete()));
           }
           if(!CollectionUtils.isEmpty(findParams.getTags())){
               Join<Task,Tag> tags = root.join("tags");
               predicates.add(criteriaBuilder.or(
                       findParams.getTags()
                       .stream()
                       .map(tag -> criteriaBuilder.equal(tags.get("id"),tag.getId()))
                       .toArray(Predicate[] :: new)
               ));
               criteriaQuery.groupBy(root.get("id"));
           }
           if(StringUtils.isNoneEmpty(findParams.getText())){
                Stream.of (findParams
                        .getText()
                        .toUpperCase()
                        .trim()
                        .split("[a-zA-Za-яА-ЯёЁ]"))
                        .forEach(key -> {
                    if (key.length()>=3){
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("text")), "%"+key+"%"));
                    }
                });
           }


           predicates.add(criteriaBuilder.isNull(root.get("parentId")));

           return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        });

    }
    public void deleteAll(){//Изменения
        resetParentId();
        taskRepo.deleteAll();
    }
    private void resetParentId(){//Изменения
        List<Task> tasks = findAll();
        tasks.forEach(task -> {
            task.setParentId(null);
        });
        taskRepo.saveAll(tasks);
    }

}
