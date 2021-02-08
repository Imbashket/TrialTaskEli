package by.kravchenko.elinexttest.impl.exceptions;

/**
 * @author Pavel Kravchenko
 */
public class ConstructorNotFoundException extends RuntimeException{
    public ConstructorNotFoundException() {
    }

    public ConstructorNotFoundException(String msg) {
        super(msg);
    }

}
