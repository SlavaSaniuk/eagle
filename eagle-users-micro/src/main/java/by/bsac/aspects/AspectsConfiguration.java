package by.bsac.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.tomcat.TomcatLoadTimeWeaver;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@Import(by.bsac.aspects.AspectsBeans.class)
public class AspectsConfiguration implements LoadTimeWeavingConfigurer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectsConfiguration.class);

    public AspectsConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(AspectsConfiguration.class));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public LoadTimeWeaver getLoadTimeWeaver() {
        return new TomcatLoadTimeWeaver();
    }
}
