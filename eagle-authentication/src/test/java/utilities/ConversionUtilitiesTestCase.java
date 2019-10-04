package utilities;

import by.bsac.utilities.ConversionsUtils;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


class ConversionUtilitiesTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversionUtilitiesTestCase.class);

    @Test
    void conversion() throws DecoderException {

        byte[] bytes = "Hello world my name is Slava".getBytes();
        LOGGER.debug("Bytes: " + Arrays.toString(bytes));

        String hex = ConversionsUtils.bytesToHex(bytes);
        byte[] from_hex = ConversionsUtils.hexToBytes(hex);
        LOGGER.debug("Bytes: " + Arrays.toString(from_hex));

        Assertions.assertArrayEquals(bytes, from_hex);


    }

}
