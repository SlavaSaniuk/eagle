package tests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tests.beans.Lou;

@Configuration
public class BeansConfiguration {

    @Bean(name = "Lou")
    public Lou getLou() {
        return new Lou();
    }

}
