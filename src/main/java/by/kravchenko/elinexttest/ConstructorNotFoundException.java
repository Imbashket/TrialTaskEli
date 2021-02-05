package by.kravchenko.elinexttest;

/**
 * @author Pavel Kravchenko
 */
public class ConstructorNotFoundException extends RuntimeException{
    public ConstructorNotFoundException() {
    }

    public ConstructorNotFoundException(String msg) {
        super(msg);
    }

    public ConstructorNotFoundException(Exception e) {
        super(e);
    }
}
