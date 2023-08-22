package spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@org.aspectj.lang.annotation.Aspect
public class Aspect {
    @Before(value = "spring.aspect.PointCuts.pointCut1()")
    public void before(JoinPoint joinPoint) {
        System.out.println("[spring.aspect] before advise");
    }

//    @Around(value = "spring.aspect.PointCuts.pointCut1()")
//    public String around(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("[spring.aspect] around advise begin");
//        Object proceed = pjp.proceed();
//        System.out.println("[spring.aspect] around advise end, old value: " + proceed);
//        return "modified by around";
//    }
//
//    @AfterReturning(value = "spring.aspect.PointCuts.pointCut1()")
//    @Order(10)
//    public void afterReturning(JoinPoint joinPoint) {
//        System.out.println("[spring.aspect] afterReturning advise");
//    }
//
//    @AfterReturning(value = "spring.aspect.PointCuts.pointCut1()")
//    @Order(9)
//    public void afterReturning2(JoinPoint joinPoint) {
//        System.out.println("[spring.aspect] afterReturning advise2");
//    }
//
//    @AfterThrowing(value = "spring.aspect.PointCuts.pointCut1()")
//    public void afterThrowing(JoinPoint joinPoint) {
//        System.out.println("[spring.aspect] afterThrowing advise");
//    }
//
//    @After(value = "spring.aspect.PointCuts.pointCut1()")
//    public void after(JoinPoint joinPoint) {
//        System.out.println("[spring.aspect] after advise");
//    }
}
