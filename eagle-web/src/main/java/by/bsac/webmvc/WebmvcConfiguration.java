package by.bsac.webmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@Import(ThymeleafConfiguration.class)
@ComponentScan(basePackages = "by.bsac.webmvc.controllers") //Enable controllers scan
public class WebmvcConfiguration implements WebMvcConfigurer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(WebmvcConfiguration.class);
    //Spring beans
    private SpringTemplateEngine template_engine; //Autowired

    //Constructor
    public WebmvcConfiguration() {
        LOGGER.info("Start to initialize " +this.getClass() +" configuration class.");
    }

    //Enable default servlet handing
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }



    //Spring beans
    @Bean(name = "viewResolver")
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(this.template_engine);
        resolver.setOrder(1); //First in charge to resolve view names
        return resolver;
    }

    @Autowired
    public void setTemplateEngine(SpringTemplateEngine a_engine) {
        LOGGER.debug("Autowire: " +a_engine.getClass().getName() +" bean.");
        this.template_engine = a_engine;
    }
}
