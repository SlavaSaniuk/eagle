package by.bsac.services.images.context;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.annotations.logging.BeforeLog;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.repositories.UserCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class ImagesContextManager implements ImagesContextService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesContextManager.class);
    //Spring beans
    //Via setters
    private UserImagesContextCrudService context_crud_service;
    private UserCrudRepository user_crud_repository;

    public ImagesContextManager() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(ImagesContextManager.class)));
    }

    @Override
    @MethodCall(withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    @BeforeLog(value = "Create new UserImagesContext entity for user[%s];", argsClasses = {User.class})
    @Transactional
    public UserImagesContext createUserImagesContext(User a_owner, UserImagesContext a_context) {

        //Get user by id
        User user = this.user_crud_repository.findById(a_owner.getUserId()).orElse(null);
        if (user == null) throw new NullPointerException(String.format("User[%s] not registered in database", a_owner));

        a_context.setImagesOwner(user);
        user.setImagesContext(a_context);

        return this.context_crud_service.create(a_context);
    }






    //Dependency management
    public void setUserImagesContextCrudService(UserImagesContextCrudService a_service) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(UserImagesContextCrudService.class), ImagesContextManager.class));
        this.context_crud_service = a_service;
    }

    public void setUserCrudRepository(UserCrudRepository a_repository) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(UserCrudRepository.class), ImagesContextManager.class));
        this.user_crud_repository = a_repository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Check dependencies
        if (this.context_crud_service == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(UserImagesContextCrudService.class)));

        if (this.user_crud_repository == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(UserCrudRepository.class)));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(ImagesContextManager.class)));
    }
}
