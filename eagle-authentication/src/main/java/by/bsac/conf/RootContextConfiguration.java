package by.bsac.conf;

import by.bsac.services.ServicesConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DatasourcesConfiguration.class, PersistenceConfiguration.class, ServicesConfiguration.class})
public class RootContextConfiguration {



}
