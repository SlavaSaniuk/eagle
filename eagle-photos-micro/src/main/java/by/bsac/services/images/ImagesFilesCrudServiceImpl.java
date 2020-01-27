package by.bsac.services.images;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.annotations.logging.BeforeLog;
import by.bsac.domain.models.ImageFile;
import by.bsac.repositories.ImagesFilesJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Service
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
    public ImageFile create(ImageFile entity) {
        return this.images_files_repository.save(entity);
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true)
    @BeforeLog(value = "Get ImageFile entity by id[%d];", argsClasses = Long.class)
    public ImageFile get(Long entity_id) {
        return this.images_files_repository.getOne(entity_id);
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true)
    @BeforeLog(value = "Delete ImageFile entity[%s];", argsClasses = ImageFile.class)
    public void delete(ImageFile entity) {
        this.images_files_repository.delete(entity);
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
