package spring.aspect;

import org.springframework.stereotype.Component;

@Component
public class TargetBean {
    public void print() {
        System.out.println("hello");
    }
}
