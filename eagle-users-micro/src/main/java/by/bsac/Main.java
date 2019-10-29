package by.bsac;

import by.bsac.configuration.DatasourcesConfig;
import by.bsac.services.ServicesConfiguration;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "by.bsac.webmvc.controllers")
@Import({DatasourcesConfig.class, ServicesConfiguration.class})
public class Main extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        //Disable banner
        builder.bannerMode(Banner.Mode.OFF);

        return builder;
    }
}
