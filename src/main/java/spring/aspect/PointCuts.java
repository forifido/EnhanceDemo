package spring.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    // @Pointcut(value = "within(spring.aspect.*)")
    @Pointcut(value = "execution(public void spring.aspect.TargetBean.print())")
    public void pointCut1() {
    }
}