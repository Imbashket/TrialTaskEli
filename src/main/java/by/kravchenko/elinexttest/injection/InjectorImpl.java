package by.kravchenko.elinexttest.injection;

import by.kravchenko.elinexttest.exceptions.BindingNotFoundException;
import by.kravchenko.elinexttest.exceptions.ConstructorNotFoundException;
import by.kravchenko.elinexttest.exceptions.TooManyConstructorsException;
import by.kravchenko.elinexttest.provider.Provider;
import by.kravchenko.elinexttest.provider.ProviderImpl;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Kravchenko
 */
public class InjectorImpl implements Injector {

    private Map<Class<?>, Class<?>> bindingsMap = new HashMap<>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type){
        Class<?> binding = bindingsMap.get(type);
        if (binding != null) {
            return new ProviderImpl<>(binding);
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
