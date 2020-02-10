package by.bsac.services.images;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.annotations.logging.BeforeLog;
import by.bsac.annotations.validation.ParameterValidation;
import by.bsac.aspects.validators.LongIdParameterValidator;
import by.bsac.domain.models.ImageFile;
import by.bsac.repositories.ImagesFilesJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class ImagesFilesCrudServiceImpl implements ImagesFilesCrudService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesFilesCrudServiceImpl.class);
    //Spring beans
    private ImagesFilesJpaRepository images_files_repository; //From DatasourcesConfiguration

    public ImagesFilesCrudServiceImpl() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(ImagesFilesCrudServiceImpl.class)));
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true)
    @BeforeLog(value = "Save ImageFile entity[%s];", argsClasses = ImageFile.class)
    @Transactional
    public ImageFile create(ImageFile entity) {
        return this.images_files_repository.save(entity);
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true)
    @BeforeLog(value = "Get ImageFile entity by id[%d];", argsClasses = Long.class)
    @ParameterValidation(value = LongIdParameterValidator.class, parametersClasses = Long.class, errorMessage = "Long ID is in invalid value.")
    public ImageFile get(Long entity_id) {
        return this.images_files_repository.getOne(entity_id);
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true)
    @BeforeLog(value = "Delete ImageFile entity[%s];", argsClasses = ImageFile.class)
    @Transactional
    public void delete(ImageFile entity) {
        this.images_files_repository.delete(entity);
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true)
    @BeforeLog(value = "Update image_path property in ImageFile entity[%s] with value [%s];", argsClasses = {ImageFile.class, String.class})
    @Transactional
    public void updateImagePath(ImageFile entity, String image_path) {
        this.images_files_repository.updateImagePath(entity.getImageId(), image_path);
    }

    //Spring dependency management
    public void setImagesFilesJpaRepository(ImagesFilesJpaRepository a_repository) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(ImagesFilesJpaRepository.class), ImagesFilesCrudServiceImpl.class));
        this.images_files_repository = a_repository;
    }

    @Override
    public void afterPropertiesSet() {

        if (this.images_files_repository == null)
            throw new BeanCreationException(DependencyManagement.Exceptions.nullProperty(ImagesFilesJpaRepository.class));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(ImagesFilesCrudServiceImpl.class)));
    }
}
