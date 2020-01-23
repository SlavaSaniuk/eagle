package by.bsac.services.images;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.repositories.UserImagesContextCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Component("UserImagesContextCrudService")
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
    public UserImagesContext create(UserImagesContext entity) {
        LOGGER.debug("Save userImageContext: " +entity);
        return this.context_repository.save(entity);
    }

    @Override
    public UserImagesContext get(Integer entity_id) {
        return null;
    }

    @Override
    public void delete(UserImagesContext entity) {

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
            throw new BeanCreationException(String.format("Spring bean dependency of [%s] bean is null.", UserImagesContextCrudRepository.class));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(UserImagesContextCrudServiceImpl.class)));
    }
}
