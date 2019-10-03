package by.bsac.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DatasourcesConfiguration.class, PersistenceConfiguration.class})
public class RootContextConfiguration {



}
