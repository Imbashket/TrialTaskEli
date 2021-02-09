package by.kravchenko.elinexttest;

import by.kravchenko.elinexttest.impl.InjectorImpl;
import by.kravchenko.elinexttest.impl.exceptions.ConstructorNotFoundException;
import by.kravchenko.elinexttest.impl.exceptions.TooManyConstructorsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Pavel Kravchenko
 */
public class InjectorImplTest {
    private static Injector injector;

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

    public static class InjectTwoConstr implements TestIntf1 {
        @Inject
        public InjectTwoConstr() {
        }

        @Inject
        public InjectTwoConstr(TestIntf2 testIntf2) {
        }
    }

    public static class TwoConstrDefaultInject implements TestIntf1 {
        @Inject
        public TwoConstrDefaultInject() {
        }

        public TwoConstrDefaultInject(TestIntf2 testIntf2) {
        }
    }

    public static class TwoConstrOneInject implements TestIntf1 {
        public TwoConstrOneInject() {
        }

        @Inject
        public TwoConstrOneInject(TestIntf2 testIntf2) {
        }
    }



    @BeforeAll
    public static void createInjector() {
        injector = InjectorImpl.getInstance();
    }

    @Test
    public void injectorShouldBeSingleton() {
        Injector injector1 = InjectorImpl.getInstance();
        Injector injector2 = InjectorImpl.getInstance();
        assertEquals(injector1, injector);
        assertEquals(injector2, injector);
    }

    @Test
    public void testBindThrowsExceptions() {
        assertThrows(ConstructorNotFoundException.class,
                () -> injector.bind(TestIntf1.class, OneDefaultConstr.class));
        injector.bind(TestIntf1.class, InjectDefaultConstr.class);
        assertThrows(TooManyConstructorsException.class,
                () -> injector.bind(TestIntf1.class, InjectTwoConstr.class));
    }

}
