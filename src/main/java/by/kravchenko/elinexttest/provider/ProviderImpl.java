package by.kravchenko.elinexttest.provider;

import by.kravchenko.elinexttest.injection.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Pavel Kravchenko
 */
public class ProviderImpl<T> implements Provider<T> {
    private Class<?> clazz;

    public ProviderImpl(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T getInstance() {
        try {
            Constructor<?>[] constructors = clazz.getConstructors();
            Class<?>[] parameterTypes = null;
            for (Constructor<?> constructor : constructors) {
                if (constructor.isAnnotationPresent(Inject.class)) {
                    parameterTypes = constructor.getParameterTypes();
                }
            }
            return (T) clazz.getDeclaredConstructor(parameterTypes).newInstance();
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException e) {
            return null;
        }

    }
}
