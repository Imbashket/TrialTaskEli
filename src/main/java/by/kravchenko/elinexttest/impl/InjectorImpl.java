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

    private final Map<Class<?>, Binding> bindings = new HashMap<>();
    private final Map<Class<?>, Provider<?>> providers = new HashMap<>();

    private InjectorImpl() {
    }

    public static InjectorImpl getInstance() {
        if (instance == null) {
            instance = new InjectorImpl();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        if (providers.containsKey(type)) {
            return (Provider<T>) providers.get(type);
        }
        Binding binding = bindings.get(type);
        if (binding == null) {
            return null;
        }
        Provider<T> provider;
        if (binding.isSingleton()) {
            provider = new ProviderSingletonImpl<>((Class<T>) binding.getBinding());
        } else {
            provider = new ProviderImpl<>((Class<T>) binding.getBinding());
        }
        providers.put(type, provider);
        return provider;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        doBind(intf, impl, false);
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        doBind(intf, impl, true);
    }

    private <T> void doBind(Class<T> intf, Class<? extends T> impl, boolean isSingleton) {
        if (bindings.containsKey(intf) && isSingleton) {
            return;
        }
        if (isInjectable(impl)) {
            bindings.put(intf, new Binding(impl.asSubclass(intf), isSingleton));
        }
    }

    private <T> boolean isInjectable(Class<T> clazz) {
        Constructor<?>[] allConstructors = clazz.getConstructors();
        int injectAnnotations = countInjectAnnotation(allConstructors);
        if (injectAnnotations > 1) {
            throw new TooManyConstructorsException(String.format("%s has too many inject annotations", clazz));
        } else if (injectAnnotations == 0) {
            throw new ConstructorNotFoundException(String.format("%s doesn't have any inject annotations", clazz));
        }
        return true;
    }

    private int countInjectAnnotation(Constructor<?>[] allConstructors) {
        int number = 0;
        for (Constructor<?> constructor : allConstructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                number++;
            }
        }
        return number;
    }

    private static class Binding {
        private final Class<?> bindingClass;
        private final boolean isSingleton;

        public Binding(Class<?> bindingClass, boolean isSingleton) {
            this.bindingClass = bindingClass;
            this.isSingleton = isSingleton;
        }

        public Class<?> getBinding() {
            return bindingClass;
        }

        public boolean isSingleton() {
            return isSingleton;
        }

    }
}
