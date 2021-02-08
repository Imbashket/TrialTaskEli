package by.kravchenko.elinexttest.impl;

import by.kravchenko.elinexttest.Provider;
import by.kravchenko.elinexttest.Inject;
import by.kravchenko.elinexttest.impl.exceptions.BindingNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Pavel Kravchenko
 */
public class ProviderImpl<T> implements Provider<T> {
    private final Class<T> clazz;

    public ProviderImpl(Class<T> clazz) {
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
            if (parameterTypes == null) {
                return clazz.getDeclaredConstructor().newInstance();
            } else {
                Object[] objArr = new Object[parameterTypes.length];
                int i = 0;
                InjectorImpl injector = InjectorImpl.getInstance();
                for (Class<?> parameter : parameterTypes) {
                    Object instance = injector.getProvider(parameter).getInstance();
                    if (instance == null) {
                        throw new BindingNotFoundException();
                    } else {
                        objArr[i] = instance;
                        i++;
                    }
                }
                return clazz.getDeclaredConstructor(parameterTypes).newInstance(objArr);
            }
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException e) {
            return null;
        }

    }
}
