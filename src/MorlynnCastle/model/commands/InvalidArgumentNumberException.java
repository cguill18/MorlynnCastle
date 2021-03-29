package MorlynnCastle.model.commands;

public class InvalidArgumentNumberException extends RuntimeException {

    public InvalidArgumentNumberException(){
        super();
    }

    public InvalidArgumentNumberException(String message){
        super(message);
    }
}
