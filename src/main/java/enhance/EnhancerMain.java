package enhance;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Method;

public class EnhancerMain {
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
        enhancer.setCallbackFilter(EnhancerMain::callBackFilter);
        enhancer.setCallbackTypes(new Class[] { MethodInterceptor.class, NoOp.class });
        Class<?> clazz = enhancer.createClass();

        Enhancer.registerCallbacks(clazz, getDefaultCallbacks());
        call(clazz.newInstance());

        // reset another callback
        Enhancer.registerCallbacks(clazz, getAnotherCallbacks());
        call(clazz.newInstance());
    }

    private static void testSetCallBacksMulitThread()
            throws InterruptedException, InstantiationException, IllegalAccessException {
        System.out.println("=== test set call back in different thread ===");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallbackFilter(EnhancerMain::callBackFilter);
        enhancer.setCallbackTypes(getCallBackTypes());
        Class<?> clazz = enhancer.createClass();

        Thread thread = new Thread(() -> {
            // set callback in another thread
            Enhancer.registerCallbacks(clazz, getDefaultCallbacks());
            try {
                call(clazz.newInstance(), "in sub thread");
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        thread.join();

        // not work for this call (thread callbacks)
        call(clazz.newInstance(), "in main thread");
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

    private static Callback[] getDefaultCallbacks() {
        return new Callback[] { new SomeHandler(), NoOp.INSTANCE };
    }

    private static Callback[] getAnotherCallbacks() {
        return new Callback[] { new AnotherHandler(), NoOp.INSTANCE };
    }

    private static Class<?>[] getCallBackTypes() {
        // return new Class[] { SomeHandler.class, NoOp.class };
        return new Class[] { MethodInterceptor.class, NoOp.class };
    }
}
