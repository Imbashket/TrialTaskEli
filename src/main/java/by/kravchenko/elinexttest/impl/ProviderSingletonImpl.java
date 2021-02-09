package by.kravchenko.elinexttest.impl;

/**
 * @author Pavel Kravchenko
 */
public class ProviderSingletonImpl<T> extends ProviderImpl<T>{
    private T instance = null;

    public ProviderSingletonImpl(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T getInstance() {
        if (instance == null) {
            instance = super.getInstance();
        }
        return instance;
    }
}
