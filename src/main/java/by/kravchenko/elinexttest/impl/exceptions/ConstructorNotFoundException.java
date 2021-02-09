package by.kravchenko.elinexttest.impl.exceptions;

/**
 * @author Pavel Kravchenko
 */
public class ConstructorNotFoundException extends RuntimeException{
    @SuppressWarnings("unused")
    public ConstructorNotFoundException() {
    }

    @SuppressWarnings("unused")
    public ConstructorNotFoundException(String msg) {
        super(msg);
    }

}
