package by.kravchenko.elinexttest;

import by.kravchenko.elinexttest.impl.InjectorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Pavel Kravchenko
 */
public class BindingTest {
    private interface EventDAO {
    }

    public static class InMemoryEventDAOImpl implements EventDAO {

        public InMemoryEventDAOImpl(Integer n) {
        }

        @Inject
        public InMemoryEventDAOImpl() {
        }
    }

    @Test
    void testExistingBinding() {
        Injector injector = InjectorImpl.getInstance(); //создаем имплементацию инжектора
        injector.bind(EventDAO.class, InMemoryEventDAOImpl.class); //добавляем в инжектор реализацию интерфейса
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class); //получаем инстанс класса из инжектора
        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertSame(InMemoryEventDAOImpl.class, daoProvider.getInstance().getClass());
    }

}
