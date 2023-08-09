import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.NoOp;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Enhancer enhancer1 = new Enhancer();
        enhancer1.setSuperclass(Target.class);
        enhancer1.setCallbackFilter(method -> {
            if (method.getName().equals("print")) {
                return 0;
            } else {
                return 1;
            }
        });
        enhancer1.setCallbackTypes(new Class[] { MethodInterceptor.class, NoOp.class });
        // enhancer1.setCallbackTypes(new Class[] { SomeHandler.class, NoOp.class });
        Class<?> clazz = enhancer1.createClass();

        // set callback
        Enhancer.registerCallbacks(clazz, new Callback[] { new SomeHandler(), NoOp.INSTANCE });
        call(clazz.newInstance());

        // reset another callback
        Enhancer.registerCallbacks(clazz, new Callback[] { new AnotherHandler(), NoOp.INSTANCE });
        call(clazz.newInstance());

        // not only create class
        Enhancer enhancer2 = new Enhancer();
        enhancer2.setSuperclass(Target.class);
        enhancer2.setCallback(new SomeHandler());
        call(enhancer2.create());
    }

    public static void call(Object o) {
        if (o instanceof Target) {
            System.out.println("---");
            ((Target) o).print();
            ((Target) o).someOtherMethod();
            System.out.println("---");
        } else {
            System.out.println("err");
        }
    }
}
