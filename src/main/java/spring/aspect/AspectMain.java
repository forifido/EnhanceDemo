package spring.aspect;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
public class AspectMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AspectMain.class);
        applicationContext.scan("spring.aspect");
        applicationContext.refresh();

        TargetBean bean = applicationContext.getBean(TargetBean.class);
        System.out.println(bean.run());

        applicationContext.close();
    }
}
