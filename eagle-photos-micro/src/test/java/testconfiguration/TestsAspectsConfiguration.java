package testconfiguration;

import by.bsac.aspects.AspectsBeans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;

import static by.bsac.core.logging.SpringCommonLogging.*;

@TestConfiguration
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@Import(AspectsBeans.class)
public class TestsAspectsConfiguration implements LoadTimeWeavingConfigurer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TestsAspectsConfiguration.class);

    public TestsAspectsConfiguration() {
        LOGGER.debug(INITIALIZATION.startInitializeConfiguration(this.getClass()));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public LoadTimeWeaver getLoadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }
}
