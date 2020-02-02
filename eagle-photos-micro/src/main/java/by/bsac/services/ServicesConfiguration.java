package by.bsac.services;

import by.bsac.configuration.properties.SystemStorageProperties;
import by.bsac.repositories.ImagesFilesJpaRepository;
import by.bsac.repositories.UserCrudRepository;
import by.bsac.repositories.UserImagesContextCrudRepository;
import by.bsac.services.images.ImagesFilesCrudService;
import by.bsac.services.images.ImagesFilesCrudServiceImpl;
import by.bsac.services.images.context.ImagesContextManager;
import by.bsac.services.images.context.ImagesContextService;
import by.bsac.services.images.context.UserImagesContextCrudService;
import by.bsac.services.images.context.UserImagesContextCrudServiceImpl;
import by.bsac.services.images.storage.StorageService;
import by.bsac.services.images.storage.SystemStorageServiceImpl;
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
    private UserCrudRepository user_crud_repository; //From DatasourceConfiguration
    private SystemStorageProperties system_storage_properties; //From RootConfiguration

    public ServicesConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(ServicesConfiguration.class));
    }

    @Bean
    public UserImagesContextCrudService getUserImagesContextCrudService() {

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

    /**
     * UserImagesContext entity manager. Default implementation is {@link ImagesContextManager} bean.
     * @return - {@link ImagesContextService} service bean.
     */
    @Bean("ImagesContextService")
    public ImagesContextService getImagesContextService() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(ImagesContextService.class)));
        ImagesContextManager service = new ImagesContextManager();

        service.setUserImagesContextCrudService(this.getUserImagesContextCrudService());
        service.setUserCrudRepository(this.user_crud_repository);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(ImagesContextService.class)));
        return service;
    }

    /**
     * Images {@link StorageService}. Commonly used implementation is {@link SystemStorageServiceImpl} bean.
     * @return - {@link StorageService} bean.
     */
    @Bean("StorageService")
    public StorageService getStorageService() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(StorageService.class)));
        SystemStorageServiceImpl service = new SystemStorageServiceImpl();

        service.setImagesFilesCrudService(this.getImagesFilesCrudService());
        service.setUserImagesContextCrudService(this.getUserImagesContextCrudService());
        service.setSystemStorageProperties(this.system_storage_properties);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(StorageService.class)));
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

    @Autowired
    public void setSystemStorageProperties(SystemStorageProperties a_props) {
        LOGGER.info(DependencyManagement.autowireViaSetter(BeanDefinition.of(SystemStorageProperties.class), ServicesConfiguration.class));
        this.system_storage_properties = a_props;
    }

    @Autowired
    public void setUserCrudRepository(UserCrudRepository a_repository) {
        LOGGER.info(DependencyManagement.autowireViaSetter(BeanDefinition.of(UserCrudRepository.class), ServicesConfiguration.class));
        this.user_crud_repository = a_repository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.context_repository == null)
            throw new BeanCreationException(DependencyManagement.Exceptions.nullProperty(UserImagesContextCrudRepository.class));

        if (this.images_files_repository == null)
            throw new BeanCreationException(DependencyManagement.Exceptions.nullProperty(ImagesFilesJpaRepository.class));

        if (this.user_crud_repository == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(UserCrudRepository.class)));

        if (this.system_storage_properties.getImages() == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(SystemStorageProperties.Images.class)));

        LOGGER.info(INITIALIZATION.endInitializeConfiguration(ServicesConfiguration.class));

    }
}
