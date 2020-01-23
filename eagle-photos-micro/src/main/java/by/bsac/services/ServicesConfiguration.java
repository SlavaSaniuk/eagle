package by.bsac.services;

import by.bsac.repositories.UserImagesContextCrudRepository;
import by.bsac.services.images.UserImagesContextCrudService;
import by.bsac.services.images.UserImagesContextCrudServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration("ServicesConfiguration")
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    //Spring beans
    //Autowired via setter
    private UserImagesContextCrudRepository context_repository;

    public ServicesConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(ServicesConfiguration.class));
    }

    @Bean
    public UserImagesContextCrudService getUserImagesContextCrudRepository() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(UserImagesContextCrudService.class)));
        UserImagesContextCrudServiceImpl service = new UserImagesContextCrudServiceImpl();

        service.setUserImagesContextCrudRepository(this.context_repository);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(UserImagesContextCrudService.class)));
        return service;
    }

    @Autowired
    public void setUserImagesContextCrudRepository(UserImagesContextCrudRepository a_context_repository) {
        LOGGER.info(DependencyManagement.autowireViaSetter(
                BeanDefinition.of(UserImagesContextCrudRepository.class), ServicesConfiguration.class));
        this.context_repository = a_context_repository;
    }
}
