package by.bsac.services;

import by.bsac.repositories.ImagesFilesJpaRepository;
import by.bsac.repositories.UserImagesContextCrudRepository;
import by.bsac.services.images.ImagesFilesCrudService;
import by.bsac.services.images.ImagesFilesCrudServiceImpl;
import by.bsac.services.images.UserImagesContextCrudService;
import by.bsac.services.images.UserImagesContextCrudServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration("ServicesConfiguration")
public class ServicesConfiguration implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    //Spring beans
    //Autowired via setter
    private UserImagesContextCrudRepository context_repository; //From DatasourceConfiguration
    private ImagesFilesJpaRepository images_files_repository; //From DatasourceConfiguration

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

    @Bean
    public ImagesFilesCrudService getImagesFilesCrudService() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(ImagesFilesCrudService.class)));
        ImagesFilesCrudServiceImpl service = new ImagesFilesCrudServiceImpl();

        service.setImagesFilesJpaRepository(this.images_files_repository);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(ImagesFilesCrudService.class)));
        return service;
    }

    //Spring autowiring
    @Autowired
    public void setUserImagesContextCrudRepository(UserImagesContextCrudRepository a_context_repository) {
        LOGGER.info(DependencyManagement.autowireViaSetter(
                BeanDefinition.of(UserImagesContextCrudRepository.class), ServicesConfiguration.class));
        this.context_repository = a_context_repository;
    }

    @Autowired
    public void setImagesFilesJpaRepository(ImagesFilesJpaRepository a_repository) {
        LOGGER.info(DependencyManagement.autowireViaSetter(
                BeanDefinition.of(ImagesFilesJpaRepository.class), ServicesConfiguration.class));
        this.images_files_repository = a_repository;
    }

    @Override
    public void afterPropertiesSet() {

        if (this.context_repository == null)
            throw new BeanCreationException(DependencyManagement.Exceptions.nullProperty(UserImagesContextCrudRepository.class));

        if (this.images_files_repository == null)
            throw new BeanCreationException(DependencyManagement.Exceptions.nullProperty(ImagesFilesJpaRepository.class));

        LOGGER.info(INITIALIZATION.endInitializeConfiguration(ServicesConfiguration.class));
    }
}
