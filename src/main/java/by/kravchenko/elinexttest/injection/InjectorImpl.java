package by.kravchenko.elinexttest.injection;

import by.kravchenko.elinexttest.exceptions.BindingNotFoundException;
import by.kravchenko.elinexttest.provider.Provider;
import by.kravchenko.elinexttest.provider.ProviderImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Kravchenko
 */
public class InjectorImpl implements Injector{

    private Map<Class<?>, Class<?>> bindingMap = new HashMap<>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) throws BindingNotFoundException {
        Provider<T> provider = new ProviderImpl<>(bindingMap.get(type));
        return provider;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        bindingMap.put(intf, impl.asSubclass(intf));
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {

    }
}
