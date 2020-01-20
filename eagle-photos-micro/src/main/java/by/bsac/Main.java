package by.bsac;

import by.bsac.configuration.RootConfiguration;
import by.bsac.webmvc.WebmvcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RootConfiguration.class, WebmvcConfiguration.class})
public class Main extends SpringBootServletInitializer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        //Disable spring boot banner
        LOGGER.info("Disable spring boot banner mode;");
        builder.bannerMode(Banner.Mode.OFF);

        return super.configure(builder);
    }
}
