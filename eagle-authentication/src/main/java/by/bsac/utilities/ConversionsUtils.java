package by.bsac.utilities;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.xml.bind.DatatypeConverter;

public class ConversionsUtils {

    public static String bytesToHex(byte[] bytes) {
       return DatatypeConverter.printHexBinary(bytes);
    }

    public static byte[] hexToBytes(String hex) throws DecoderException {
        return DatatypeConverter.parseHexBinary(hex);
    }
}
