package pl.javastart.library.exception;

public class NoSuchFileException extends RuntimeException{
    public NoSuchFileException(String message) {
        super(message);
    }
}
