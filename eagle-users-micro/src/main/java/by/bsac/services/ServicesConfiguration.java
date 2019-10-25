package by.bsac.services;

import by.bsac.repositories.DetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@Configuration
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    //Spring beans
    private DetailsRepository detail_repository;

    public ServicesConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(this.getClass()));
    }

    @Bean("DetailsManager")
    public DetailsManager createDetailsManager() {

        final UserDetailsManager dm = new UserDetailsManager();
        LOGGER.info(CREATION.beanCreationStart(dm.getClass()));

        LOGGER.info(DEPENDENCY.viaSetter(this.detail_repository.getClass(), this.getClass()));
        dm.setDetailsRepository(this.detail_repository);

        LOGGER.info(CREATION.beanCreationFinish(dm.getClass()));
        return dm;
    }

    @Autowired
    public void setDetailRepository(DetailsRepository detail_repository) {
        LOGGER.info(AUTOWIRING.viaSetter(detail_repository.getClass(), this.getClass()));
        this.detail_repository = detail_repository;
    }
}
