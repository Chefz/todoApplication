package ToDo.exception;

public class AlreadyCompletedException extends RuntimeException{
    public AlreadyCompletedException(Long id){
        super("Задача уже завершена id: "+id);
    }
}
