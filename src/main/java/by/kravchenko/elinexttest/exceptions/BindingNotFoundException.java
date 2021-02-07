package by.kravchenko.elinexttest.exceptions;

/**
 * @author Pavel Kravchenko
 */
public class BindingNotFoundException extends RuntimeException{
    public BindingNotFoundException() {
    }

    public BindingNotFoundException(String msg) {
        super(msg);
    }

    public BindingNotFoundException(Exception e) {
        super(e);
    }
}
