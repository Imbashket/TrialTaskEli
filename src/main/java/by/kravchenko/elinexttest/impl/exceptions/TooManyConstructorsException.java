package by.kravchenko.elinexttest.impl.exceptions;

/**
 * @author Pavel Kravchenko
 */
public class TooManyConstructorsException extends RuntimeException{
    @SuppressWarnings("unused")
    public TooManyConstructorsException() {
    }

    @SuppressWarnings("unused")
    public TooManyConstructorsException(String msg) {
        super(msg);
    }

}
