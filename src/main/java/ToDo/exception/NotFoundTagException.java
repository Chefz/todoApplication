package ToDo.exception;

public class NotFoundTagException extends RuntimeException{
    public NotFoundTagException(Long id){
        super("Не найден Тэг с id: "+id);
    }
}
