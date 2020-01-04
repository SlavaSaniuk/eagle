package by.bsac.services;

import by.bsac.models.xml.FeignServersModel;
import by.bsac.repositories.DetailsRepository;
import by.bsac.repositories.UserRepository;
import by.bsac.services.xml.XmlConverter;
import by.bsac.services.xml.XmlConverterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBException;
import java.io.File;

import static by.bsac.core.logging.SpringCommonLogging.*;


@Configuration
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    //Spring beans
    private DetailsRepository detail_repository;
    private UserRepository user_repository;

    public ServicesConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(ServicesConfiguration.class));
    }

    //Beans definition
    @Bean("DetailsManager")
    public DetailsManager createDetailsManager() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("DetailsManager").ofClass(DetailsManager.class)));
        final UserDetailsManager dm = new UserDetailsManager();

        LOGGER.info(DependencyManagement.setViaSetter(BeanDefinition.of(DetailsRepository.class), DetailsManager.class));
        dm.setDetailsRepository(this.detail_repository);

        LOGGER.info(DependencyManagement.setViaSetter(BeanDefinition.of(UserRepository.class), DetailsManager.class));
        dm.setUserRepository(this.user_repository);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("DetailsManager").ofClass(DetailsManager.class)));
        return dm;
    }

    @SuppressWarnings("ConstantConditions")
    @Bean("FeignServersModelXmlConverter")
    public XmlConverter<FeignServersModel> getFeignServersModelXmlConverter() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("FeignServersModelXmlConverter").ofClass(XmlConverter.class).forGenericType(FeignServersModel.class)));
        File xml_file = new File(this.getClass().getClassLoader().getResource("feign-servers.xml").getPath());

        XmlConverter<FeignServersModel> parser = null;
        try {
            parser = new XmlConverterImpl<>(FeignServersModel.class, xml_file);
        } catch (JAXBException e) {
            LOGGER.info(CREATION.creationThrowExceptionWithMessage(
                    BeanDefinition.of("FeignServersModelXmlConverter").ofClass(XmlConverter.class).forGenericType(FeignServersModel.class), e));
            throw new BeanCreationException(e.getMessage());
        }

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("FeignServersModelXmlConverter").ofClass(XmlConverter.class).forGenericType(FeignServersModel.class)));
        return parser;
    }

    //Spring autowiring
    @Autowired
    public void setDetailRepository(DetailsRepository detail_repository) {
        LOGGER.info(DependencyManagement.autowireViaSetter(BeanDefinition.of(DetailsRepository.class), ServicesConfiguration.class));
        this.detail_repository = detail_repository;
    }

    @Autowired
    public void setUserRepository(UserRepository user_repository) {
        LOGGER.info(DependencyManagement.autowireViaSetter(BeanDefinition.of(UserRepository.class), ServicesConfiguration.class));
        this.user_repository = user_repository;
    }
}
