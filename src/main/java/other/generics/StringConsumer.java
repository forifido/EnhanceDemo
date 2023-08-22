package other.generics;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class StringConsumer implements Consumer<String> {
    public static void main(String[] args) {
        System.out.println(getMethods(StringConsumer.class));
        System.out.println("------");
        System.out.println(getDeclaredMethods(StringConsumer.class));
    }

    // 不能获取私有方法
    // 但能获取到继承的方法
    public static String getMethods(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        return convertMethodsToString(methods);
    }

    // 刚好和 getMethods 相反
    // 可以获取私有方法
    // 但是不能获取集成的方法
    public static String getDeclaredMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        return convertMethodsToString(declaredMethods);
    }

    private static String convertMethodsToString(Method[] declaredMethods) {
        StringBuilder ret = new StringBuilder();
        for (Method method : declaredMethods) {
            StringBuilder parameterTypes = new StringBuilder("(");
            for (Class<?> param : method.getParameterTypes()) {
                if (parameterTypes.length() > 1) {
                    parameterTypes.append(",");
                }
                parameterTypes.append(param.getName());
            }

            parameterTypes.append(")");

            if (ret.length() > 0) {
                ret.append("\n");
            }
            ret.append(method.getReturnType()).append(" ").append(method.getName()).append(parameterTypes);
        }
        return ret.toString();
    }

    @Override
    public void accept(String s) {
        System.out.println(s);
    }

    private void privateMethod() {
        System.out.println();
    }
}
