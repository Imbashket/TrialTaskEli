package by.kravchenko.elinexttest.exceptions;

/**
 * @author Pavel Kravchenko
 */
public class TooManyConstructorsException extends RuntimeException{
    public TooManyConstructorsException() {
    }

    public TooManyConstructorsException(String msg) {
        super(msg);
    }

    public TooManyConstructorsException(Exception e) {
        super(e);
    }
}
