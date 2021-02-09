package by.kravchenko.elinexttest.impl.exceptions;

/**
 * @author Pavel Kravchenko
 */
public class BindingNotFoundException extends RuntimeException{
    @SuppressWarnings("unused")
    public BindingNotFoundException() {
    }

    @SuppressWarnings("unused")
    public BindingNotFoundException(String msg) {
        super(msg);
    }

}
