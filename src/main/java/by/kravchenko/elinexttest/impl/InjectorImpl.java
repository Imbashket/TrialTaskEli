package by.kravchenko.elinexttest.impl;

import by.kravchenko.elinexttest.Inject;
import by.kravchenko.elinexttest.Injector;
import by.kravchenko.elinexttest.impl.exceptions.ConstructorNotFoundException;
import by.kravchenko.elinexttest.impl.exceptions.TooManyConstructorsException;
import by.kravchenko.elinexttest.Provider;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Kravchenko
 */
public class InjectorImpl implements Injector {
    private static InjectorImpl instance;

    private InjectorImpl(){
    }

    public static InjectorImpl getInstance() {
        if (instance == null) {
            instance = new InjectorImpl();
        }
        return instance;
    }

    private final Map<Class<?>, Class<?>> bindingsMap = new HashMap<>();

    @SuppressWarnings("rawtypes")
    @Override
    public <T> Provider<T> getProvider(Class<T> type){
        Class binding =  bindingsMap.get(type);
        if (binding != null) {
            return new ProviderImpl<> (binding);
        }
        return null;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        Constructor<?>[] allConstructors = impl.getConstructors();
        int constructorsNumber = countNumberOfConstructorInjection(allConstructors);
        if (constructorsNumber > 1) {
            throw new TooManyConstructorsException();
        } else if (constructorsNumber == 0) {
            throw new ConstructorNotFoundException();
        } else {
            bindingsMap.put(intf, impl.asSubclass(intf));
        }
    }

    private int countNumberOfConstructorInjection(Constructor<?>[] allConstructors) {
        int number = 0;
        for (Constructor<?> constructor : allConstructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                number++;
            }
        }
        return number;
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {

    }

}
