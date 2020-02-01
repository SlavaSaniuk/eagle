package by.bsac.configuration;

import by.bsac.configuration.properties.DatasourcesProperties;
import by.bsac.configuration.properties.SystemStorageProperties;
import by.bsac.services.images.storage.StorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

@ActiveProfiles("DATASOURCE_TESTS")
@SpringBootTest(classes = RootConfiguration.class)
public class ContextsConfigurationIntegrationTest{

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextsConfigurationIntegrationTest.class);

    //Spring beans
    @Autowired
    private ApplicationContext CONTEXT;

    @Test
    void getDatasourcesProperties_datasourcesPropertiesBean_shouldReturnDatasourcePropertiesBean() {
        DatasourcesProperties props = this.CONTEXT.getBean(DatasourcesProperties.class);

        Assertions.assertNotNull(props);

        LOGGER.debug("Development: " +props.getTest());
    }

    @Test
    void getTestDataSource_datasourceTestsActiveProfile_shouldReturnDatasourceBean() {

        DataSource ds = this.CONTEXT.getBean(DataSource.class);

        Assertions.assertNotNull(ds);

        LOGGER.debug("Datasource: " +ds);

    }

    @Test
    void getSystemStorageProperties_enableConfigurationPropertiesAnnotation_shouldReturnSystemStorageServiceBean() {

        SystemStorageProperties props = this.CONTEXT.getBean(SystemStorageProperties.class);

        Assertions.assertNotNull(props);

        LOGGER.debug("SystemStorageProperties: " +props);
    }

    @Test
    void getStorageService_SystemStorageServiceImpl_shouldReturnStorageServiceBean() {

        StorageService service = this.CONTEXT.getBean(StorageService.class);

        Assertions.assertNotNull(service);

        LOGGER.debug("Storage service: " +service);

    }
}
