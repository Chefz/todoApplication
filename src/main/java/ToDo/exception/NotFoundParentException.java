package ToDo.exception;

public class NotFoundParentException extends RuntimeException{
    public NotFoundParentException(Long id){
        super("Не найдена главная задача подзадачи: "+ id);
    }
}
