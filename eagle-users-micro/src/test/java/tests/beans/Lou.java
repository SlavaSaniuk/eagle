package tests.beans;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import org.springframework.stereotype.Component;

@Component
public class Lou {

    @MethodCall(withArgs = true, withReturnType = true)
    @MethodExecutionTime(inNanos = false, inMicros = true)
    public void sayLou(String msg) {
        System.out.println("Lou say: " +msg);
    }

}
