package spring.aspect;

import org.springframework.stereotype.Component;

@Component
public class TargetBean {
    public String run() {
        return "hi";
    }
}
