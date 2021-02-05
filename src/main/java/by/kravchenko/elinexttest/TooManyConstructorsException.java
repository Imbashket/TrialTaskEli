package by.kravchenko.elinexttest;

/**
 * @author Pavel Kravchenko
 */
public class TooManyConstructorsException extends Exception{
    public TooManyConstructorsException() {
    }

    public TooManyConstructorsException(String msg) {
        super(msg);
    }

    public TooManyConstructorsException(Exception e) {
        super(e);
    }
}
