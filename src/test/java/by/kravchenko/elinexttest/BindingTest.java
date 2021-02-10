package by.kravchenko.elinexttest;

import by.kravchenko.elinexttest.impl.InjectorImpl;
import by.kravchenko.elinexttest.impl.exceptions.BindingNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Pavel Kravchenko
 */
public class BindingTest {
    private interface EventDAO {
    }

    @SuppressWarnings("unused")
    public static class InMemoryEventDAOImpl implements EventDAO {
        public InMemoryEventDAOImpl() {
        }

        @Inject
        public InMemoryEventDAOImpl(SomeIntf someIntf) {
        }
    }

    private interface SomeIntf {
    }

    public static class SomeClass implements SomeIntf {
        @Inject
        public SomeClass() {
        }

    }

    @AfterEach
    public void resetSingletonInjector() throws Exception {
        Field instance = InjectorImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void testPrototypeBindingClasses() {
        Injector injector = InjectorImpl.getInstance(); //создаем имплементацию инжектора
        injector.bind(EventDAO.class, InMemoryEventDAOImpl.class); //добавляем в инжектор реализацию интерфейса
        injector.bind(SomeIntf.class, SomeClass.class);
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class); //получаем инстанс класса из инжектора
        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertSame(InMemoryEventDAOImpl.class, daoProvider.getInstance().getClass());
    }

    @Test
    public void testSingletonBindingClasses() {
        Injector injector = InjectorImpl.getInstance();
        injector.bindSingleton(EventDAO.class, InMemoryEventDAOImpl.class);
        injector.bind(SomeIntf.class, SomeClass.class);
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class);
        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertEquals(daoProvider.getInstance(), daoProvider.getInstance());
        assertSame(InMemoryEventDAOImpl.class, daoProvider.getInstance().getClass());
    }

    @Test
    public void testUnboundClassInConstructor() {
        Injector injector = InjectorImpl.getInstance();
        injector.bindSingleton(EventDAO.class, InMemoryEventDAOImpl.class);
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class);
        //SomeClass is not bound
        assertThrows(BindingNotFoundException.class, daoProvider::getInstance);
    }
}
