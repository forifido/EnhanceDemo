package spring.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    // @Pointcut(value = "within(spring.aspect.*)")
    @Pointcut(value = "execution(public String spring.aspect.TargetBean.run())")
    public void pointCut1() {
    }
}