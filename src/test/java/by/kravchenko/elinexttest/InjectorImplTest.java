package by.kravchenko.elinexttest;

import by.kravchenko.elinexttest.impl.InjectorImpl;
import by.kravchenko.elinexttest.impl.exceptions.ConstructorNotFoundException;
import by.kravchenko.elinexttest.impl.exceptions.TooManyConstructorsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Pavel Kravchenko
 */
public class InjectorImplTest {

    @BeforeEach
    public void resetSingletonInjector() throws Exception {
        Field instance = InjectorImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    private interface TestIntf1 {
    }

    private interface TestIntf2 {
    }

    public static class OneDefaultConstr implements TestIntf1 {
        public OneDefaultConstr() {
        }
    }

    public static class InjectDefaultConstr implements TestIntf1 {
        @Inject
        public InjectDefaultConstr() {
        }
    }

    @SuppressWarnings("unused")
    public static class InjectTwoConstr implements TestIntf1 {
        @Inject
        public InjectTwoConstr() {
        }

        @Inject
        public InjectTwoConstr(TestIntf2 testIntf2) {
        }
    }

    @SuppressWarnings("unused")
    public static class TwoConstrDefaultInject implements TestIntf1 {
        @Inject
        public TwoConstrDefaultInject() {
        }

        public TwoConstrDefaultInject(TestIntf2 testIntf2) {
        }
    }

    @SuppressWarnings("unused")
    public static class TwoConstrParameterizedInject implements TestIntf1 {
        public TwoConstrParameterizedInject() {
        }

        @Inject
        public TwoConstrParameterizedInject(TestIntf2 testIntf2) {
        }
    }

    @Test
    public void injectorShouldBeSingleton() {
        Injector injector = InjectorImpl.getInstance();
        Injector injector1 = InjectorImpl.getInstance();
        Injector injector2 = InjectorImpl.getInstance();
        assertEquals(injector1, injector);
        assertEquals(injector2, injector);
    }

    @Test
    public void testBindThrowsExceptions() {
        Injector injector = InjectorImpl.getInstance();
        injector.bind(TestIntf1.class, InjectDefaultConstr.class);
        injector.bind(TestIntf1.class, TwoConstrDefaultInject.class);
        injector.bind(TestIntf1.class, TwoConstrParameterizedInject.class);
        assertThrows(ConstructorNotFoundException.class,
                () -> injector.bind(TestIntf1.class, OneDefaultConstr.class));
        assertThrows(TooManyConstructorsException.class,
                () -> injector.bind(TestIntf1.class, InjectTwoConstr.class));
    }

   @Test
    public void instanceShouldBeGotFromFirstSingletonBinding() {
        Injector injector = InjectorImpl.getInstance();
        injector.bindSingleton(TestIntf1.class, InjectDefaultConstr.class);
        injector.bindSingleton(TestIntf1.class, TwoConstrDefaultInject.class);
        injector.bindSingleton(TestIntf1.class, TwoConstrParameterizedInject.class);
        Provider<TestIntf1> daoProvider = injector.getProvider(TestIntf1.class);
        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertSame(InjectDefaultConstr.class, daoProvider.getInstance().getClass());
    }
}

