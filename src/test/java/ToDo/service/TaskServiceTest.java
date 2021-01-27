package ToDo.service;

import ToDo.entity.Task;
import ToDo.exception.NotFoundException;
import ToDo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepo;

    @InjectMocks
    private TaskService taskService;
    private final Task task = Task.builder()
            .id(100L)
            .text("Task_text")
            .updateDate(new Date())
            .build();
    @Test
    public void findAll(){

        when(taskRepo.findAll()).thenReturn(Collections.singletonList(task));

        List<Task> result = taskService.findAll();

        assertThat(result, hasSize(1));
        assertThat(result.get(0), equalTo(task));
    }

    @Test
    public void findById(){

        when(taskRepo.findById(0L)).thenReturn(java.util.Optional.ofNullable(task));

        Task taskTest = taskService.findById(0L);

        assertThat(taskTest, equalTo(task));
    }
    @Test
    public void findByIdIfNull(){

         when(taskRepo.findById(0L)).thenReturn(Optional.empty());
        //when(taskRepo.findById(0L)).thenReturn(java.util.Optional.ofNullable(task));

         try {
              Task taskTest = taskService.findById(0L);
         }
         catch (NotFoundException e){
             assertThat(e.getMessage(),equalTo("Not Found: "+0L));
             return;
         }
         catch (Throwable e){
             fail();
         }
         fail();

    }
    @Test
    public void addTest(){
        when(taskRepo.save(task)).thenReturn(task);

        Task taskTest = taskService.add(task);

        assertThat(taskTest, equalTo(task));

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.SECOND, -1);
        assertThat(task.getCreateDate(),greaterThan(calendar.getTime()));
        assertThat(taskTest.getCreateDate(), equalTo(task.getCreateDate()));
        assertThat(taskTest.getIsComplete(),equalTo(task.getIsComplete()));
        assertThat(taskTest.getUpdateDate(),equalTo(task.getUpdateDate()));
    }
    @Test
    public void addIsCalledTest(){

        Task taskTest = taskService.add(task);

        verify(taskRepo, times(1)).save(task);
    }
    //Обновление и удаление самостоятельно


   @Test
    public void deleteTest(){

        doNothing().when(taskRepo).deleteById(100L);
        when(taskRepo.findById(100L)).thenReturn(Optional.ofNullable(task));

        taskService.delete(100L);

        verify(taskRepo,times(1)).deleteById(100L);
   }
}
