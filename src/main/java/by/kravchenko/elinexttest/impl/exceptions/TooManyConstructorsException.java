package by.kravchenko.elinexttest.impl.exceptions;

/**
 * @author Pavel Kravchenko
 */
public class TooManyConstructorsException extends RuntimeException{
    public TooManyConstructorsException() {
    }

    public TooManyConstructorsException(String msg) {
        super(msg);
    }

}
