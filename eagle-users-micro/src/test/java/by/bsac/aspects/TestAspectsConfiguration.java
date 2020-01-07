package by.bsac.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.LoadTimeWeavingConfigurer;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@Import(AspectsBeans.class)
public class TestAspectsConfiguration implements LoadTimeWeavingConfigurer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TestAspectsConfiguration.class);

    public TestAspectsConfiguration() {
        LOGGER.debug(INITIALIZATION.startInitializeConfiguration(TestAspectsConfiguration.class));
    }

    @Override
    public LoadTimeWeaver getLoadTimeWeaver() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of("loadTimeWeaver").ofClass(InstrumentationLoadTimeWeaver.class)));
        return new InstrumentationLoadTimeWeaver();
    }

}
