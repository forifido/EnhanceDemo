package spring.proxyfactory;

import org.springframework.aop.Advisor;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactoryMain {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new Target());
        List<Advisor> advisors = new ArrayList<>();
        // exe after, print returnValue
        advisors.add(new DefaultPointcutAdvisor(new AfterReturningAdvice() {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
                    throws Throwable {
                System.out.println(returnValue);
            }
        }));
        advisors.add(new DefaultPointcutAdvisor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("begin exe: " + method.getName());
            }
        }));
        // exe before, modify returnValue
        advisors.add(new DefaultPointcutAdvisor(new AfterReturningAdvice() {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
                    throws Throwable {
                Target.Result result = (Target.Result) returnValue;
                result.setCode("ok");
            }
        }));

        proxyFactory.addAdvisors(advisors);
        Target proxy = (Target) proxyFactory.getProxy();
        proxy.run();
    }
}
