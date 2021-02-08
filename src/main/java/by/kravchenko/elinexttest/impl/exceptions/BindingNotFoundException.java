package by.kravchenko.elinexttest.impl.exceptions;

/**
 * @author Pavel Kravchenko
 */
public class BindingNotFoundException extends RuntimeException{
    public BindingNotFoundException() {
    }

    public BindingNotFoundException(String msg) {
        super(msg);
    }

}
