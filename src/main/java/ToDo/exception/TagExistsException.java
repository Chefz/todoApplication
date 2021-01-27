package ToDo.exception;

public class TagExistsException extends RuntimeException{
    public TagExistsException(){
        super("Тэг уже существует ");
    }
}
