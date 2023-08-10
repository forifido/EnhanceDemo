import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InterruptedException {
        testResetCallbacks();
        testSetCallBacksMulitThread();
        testNotOnlyCreateClass();
    }

    private static void testNotOnlyCreateClass() {
        System.out.println("=== test not only create class ===");
        // not only create class
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallback(new SomeHandler());
        call(enhancer.create());
    }

    private static void testResetCallbacks() throws InstantiationException, IllegalAccessException {
        System.out.println("=== test reset callbacks ===");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallbackFilter(Main::callBackFilter);
        enhancer.setCallbackTypes(new Class[] { MethodInterceptor.class, NoOp.class });
        Class<?> clazz = enhancer.createClass();

        Enhancer.registerCallbacks(clazz, new Callback[] { new SomeHandler(), NoOp.INSTANCE });
        call(clazz.newInstance());

        // reset another callback
        Enhancer.registerCallbacks(clazz, new Callback[] { new AnotherHandler(), NoOp.INSTANCE });
        call(clazz.newInstance());

    }

    private static void testSetCallBacksMulitThread()
            throws InterruptedException, InstantiationException, IllegalAccessException {
        System.out.println("=== test set call back in different thread ===");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallbackFilter(Main::callBackFilter);
        enhancer.setCallbackTypes(new Class[] { MethodInterceptor.class, NoOp.class });
        // enhancer1.setCallbackTypes(new Class[] { SomeHandler.class, NoOp.class });
        Class<?> clazz = enhancer.createClass();

        Thread thread = getThread(() -> {
            // set callback in another thread
            Enhancer.registerCallbacks(clazz, new Callback[] { new SomeHandler(), NoOp.INSTANCE });
            try {
                call(clazz.newInstance(), "in sub thread");
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        thread.join();

        call(clazz.newInstance(), "in main thread");
    }

    private static Thread getThread(Runnable runnable) {
        Thread thread = new Thread(runnable);

        thread.start();
        return thread;
    }

    public static void call(Object o, String prefix) {
        if (o instanceof Target) {
            System.out.println(prefix + "--- begin");
            ((Target) o).print();
            ((Target) o).someOtherMethod();
            System.out.println(prefix + "--- end");
        } else {
            System.out.println("err");
        }
    }

    public static void call(Object o) {
        call(o, "");
    }

    private static int callBackFilter(Method method) {
        if (method.getName().equals("print")) {
            return 0;
        } else {
            return 1;
        }
    }
}
