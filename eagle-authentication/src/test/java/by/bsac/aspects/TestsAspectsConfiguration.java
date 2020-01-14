package by.bsac.aspects;

import by.bsac.core.logging.SpringCommonLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.LoadTimeWeavingConfigurer;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;

@SuppressWarnings("NullableProblems")
@Configuration("TestsAspectsConfiguration")
@Import(AspectsBeans.class)
public class TestsAspectsConfiguration implements LoadTimeWeavingConfigurer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TestsAspectsConfiguration.class);

    public TestsAspectsConfiguration() {
        LOGGER.debug(SpringCommonLogging.INITIALIZATION.startInitializeConfiguration(TestsAspectsConfiguration.class));
    }

    @Override
    public LoadTimeWeaver getLoadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }
}
