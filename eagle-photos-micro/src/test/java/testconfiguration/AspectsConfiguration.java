package testconfiguration;

import by.bsac.aspects.AspectsBeans;
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
public class AspectsConfiguration implements LoadTimeWeavingConfigurer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectsConfiguration.class);

    public AspectsConfiguration() {
        LOGGER.debug(INITIALIZATION.startInitializeConfiguration(this.getClass()));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public LoadTimeWeaver getLoadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }
}
