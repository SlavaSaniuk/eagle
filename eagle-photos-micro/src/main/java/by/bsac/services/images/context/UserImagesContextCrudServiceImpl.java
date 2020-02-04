package by.bsac.services.images.context;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.annotations.logging.BeforeLog;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.repositories.UserImagesContextCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class UserImagesContextCrudServiceImpl implements UserImagesContextCrudService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserImagesContextCrudServiceImpl.class);
    //Spring beans
    private UserImagesContextCrudRepository context_repository; //Set via setter

    public UserImagesContextCrudServiceImpl() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(UserImagesContextCrudServiceImpl.class)));
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true)
    @BeforeLog(value = "Save UserImagesContext entity[%s];", argsClasses = UserImagesContext.class)
    @Transactional
    public UserImagesContext create(UserImagesContext entity) {
        return this.context_repository.save(entity);
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true)
    @BeforeLog(value = "Get UserImagesContext entity with ID[%d];", argsClasses = Integer.class)
    @Nullable public UserImagesContext get(Integer entity_id) {
        return this.context_repository.findById(entity_id).orElse(null);
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true)
    @BeforeLog(value = "Delete UserImagesContext entity[%s];", argsClasses = UserImagesContext.class)
    public void delete(UserImagesContext entity) {
        this.context_repository.delete(entity);
    }

    //Spring dependencies
    public void setUserImagesContextCrudRepository(UserImagesContextCrudRepository a_repository) {
        LOGGER.debug(DependencyManagement.setViaSetter(
                BeanDefinition.of(UserImagesContextCrudRepository.class), UserImagesContextCrudServiceImpl.class));
        this.context_repository = a_repository;
    }

    @Override
    public void afterPropertiesSet() {

        if (this.context_repository == null)
            throw new BeanCreationException(DependencyManagement.Exceptions.nullProperty(UserImagesContextCrudRepository.class));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(UserImagesContextCrudServiceImpl.class)));
    }
}
