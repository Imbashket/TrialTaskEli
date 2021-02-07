package by.kravchenko.elinexttest.provider;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Pavel Kravchenko
 */
public class ProviderImpl<T> implements Provider<T>{
    private Class<?> clazz;

    public ProviderImpl(Class<?> clazz) {
        this.clazz = clazz;
    }
    @Override
    public T getInstance(){

        try {
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException e) {
            return null;
        }

    }
}
