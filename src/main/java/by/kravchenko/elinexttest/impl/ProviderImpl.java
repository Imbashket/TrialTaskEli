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
            Class<?>[] parameterTypes = getParametersTypesInConstructor(clazz);
            // check default constructor
            if (parameterTypes == null) {
                return clazz.getDeclaredConstructor().newInstance();
            } else {
                return clazz.getDeclaredConstructor(parameterTypes)
                        .newInstance(getObjectsForParameters(parameterTypes));
            }
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    private Class<?>[] getParametersTypesInConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Class<?>[] parametersTypes = null;
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                parametersTypes = constructor.getParameterTypes();
            }
        }
        return parametersTypes;
    }

    private Object[] getObjectsForParameters(Class<?>[] parameterTypes) {
        Object[] objArr = new Object[parameterTypes.length];
        int i = 0;
        InjectorImpl injector = InjectorImpl.getInstance();
        for (Class<?> parameter : parameterTypes) {
            Provider<?> provider = injector.getProvider(parameter);
            if (provider == null) {
                throw new BindingNotFoundException(String.format("Binding for %s doesn't exist", parameter));
            }
            Object instance = provider.getInstance();
            if (instance == null) {
                throw new BindingNotFoundException(String.format("Can't get instance from provider %s", provider));
            } else {
                objArr[i] = instance;
                i++;
            }
        }
        return objArr;
    }
}
