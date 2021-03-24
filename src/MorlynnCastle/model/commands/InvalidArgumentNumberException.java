package commands;

public class InvalidArgumentNumberException extends RuntimeException {

    public InvalidArgumentNumberException(){
        super();
    }

    public InvalidArgumentNumberException(String message){
        super(message);
    }
}
