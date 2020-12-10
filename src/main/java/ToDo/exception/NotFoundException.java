package ToDo.exception;

public class NotFoundException  extends RuntimeException{
    public NotFoundException(Long id){
            super("Not Found: " + id);
    }
}
