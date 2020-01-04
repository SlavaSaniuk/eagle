package by.bsac.services;

import by.bsac.core.logging.SpringCommonLogging;
import by.bsac.models.User;
import by.bsac.models.xml.FeignServersModel;
import by.bsac.repositories.DetailsRepository;
import by.bsac.repositories.UserRepository;
import by.bsac.services.xml.XmlConverter;
import by.bsac.services.xml.XmlConverterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URISyntaxException;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@Configuration
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    //Spring beans
    private DetailsRepository detail_repository;
    private UserRepository user_repository;

    public ServicesConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(this.getClass()));
    }

    @Bean("DetailsManager")
    public DetailsManager createDetailsManager() {

        final UserDetailsManager dm = new UserDetailsManager();
        LOGGER.info(CREATION.beanCreationStart(dm.getClass()));

        LOGGER.info(DEPENDENCY.viaSetter(this.detail_repository.getClass(), dm.getClass()));
        dm.setDetailsRepository(this.detail_repository);

        LOGGER.info(DEPENDENCY.viaSetter(UserRepository.class, dm.getClass()));
        dm.setUserRepository(this.user_repository);

        LOGGER.info(CREATION.beanCreationFinish(dm.getClass()));
        return dm;
    }

    @SuppressWarnings("ConstantConditions")
    @Bean("FeignServersModelXmlConverter")
    public XmlConverter<FeignServersModel> getFeignServersModelXmlConverter() throws JAXBException {

        LOGGER.info(SpringCommonLogging.CREATION.startCreateBean(SpringCommonLogging.BeanDefinition.of("FeignServersModelXmlConverter").ofClass(XmlConverter.class).forGenericType(FeignServersModel.class)));

        File xml_file = new File(this.getClass().getClassLoader().getResource("feign-servers.xml").getPath());

        XmlConverter<FeignServersModel> parser = new XmlConverterImpl<>(FeignServersModel.class, xml_file);

        return parser;
    }




    @Autowired
    public void setDetailRepository(DetailsRepository detail_repository) {
        LOGGER.info(AUTOWIRING.viaSetter(detail_repository.getClass(), this.getClass()));
        this.detail_repository = detail_repository;
    }

    @Autowired
    public void setUserRepository(UserRepository user_repository) {
        LOGGER.info(AUTOWIRING.viaSetter(user_repository.getClass(), this.getClass()));
        this.user_repository = user_repository;
    }
}
