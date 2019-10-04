package service;

import by.bsac.services.security.hashing.HashAlgorithm;
import by.bsac.services.security.hashing.PasswordHasher;
import by.bsac.utilities.ConversionsUtils;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

class PasswordHasherTestCase {

    private  PasswordHasher hasher;
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordHasherTestCase.class);

    @BeforeEach
    void setUpBeforeEach() {
        this.hasher = new PasswordHasher();
    }

    @AfterEach
    void tierDownAfterEach() {
        this.hasher = null;
    }


    @Test
    void generateSalt_sha_512_returnByteArray() {

        this.hasher.setHashAlgorithm(HashAlgorithm.SHA_512);

        byte[] salt = this.hasher.salt();

        Assertions.assertNotNull(salt);
        Assertions.assertEquals(64, salt.length);

        LOGGER.debug(ConversionsUtils.bytesToHex(salt));
    }

    @Test
    void hash_sha_256_returnByteArray() {
        this.hasher.setHashAlgorithm(HashAlgorithm.SHA_256);

        byte[] hash = hasher.hash("Hello");

        Assertions.assertNotNull(hash);
        Assertions.assertEquals(32, hash.length);

        LOGGER.debug("Hash bytes:" + Arrays.toString(hash));

        String hash_string = ConversionsUtils.bytesToHex(hash);

        LOGGER.debug(hash_string);
        Assertions.assertEquals(64, hash_string.length());

        LOGGER.debug("Bytes from string " +Arrays.toString(hash_string.getBytes(StandardCharsets.UTF_8)));


    }

    @Test
    void hashPassword_sha_1_returnByteArray() {

        this.hasher.setHashAlgorithm(HashAlgorithm.SHA_1);

        byte[] salt = this.hasher.salt();

        byte[] password_hash = this.hasher.hashPassword("I am a password", salt);

        Assertions.assertNotNull(password_hash);
        Assertions.assertEquals(20, password_hash.length);

        LOGGER.debug(ConversionsUtils.bytesToHex(password_hash));

    }

    @Test
    void hashPassword_SHA_512_returnByteArray() throws DecoderException, NoSuchAlgorithmException {

        String password = "Hello world!";
        this.hasher.setHashAlgorithm(HashAlgorithm.SHA_512);

        //Generate salt
        byte[] salt = this.hasher.salt();

        byte[] auto_hash = this.hasher.hashPassword(password, salt);
        LOGGER.debug("Password auto hash: " +ConversionsUtils.bytesToHex(auto_hash));

        //Manual generate password hash
        byte[] pass_hash = this.hasher.hash(password);

        //Save salt as string and get bytes from it
        byte[] saved_salt = ConversionsUtils.hexToBytes(ConversionsUtils.bytesToHex(salt));

        byte[] concatenated = new byte[pass_hash.length + saved_salt.length];

        System.arraycopy(pass_hash, 0, concatenated, 0, pass_hash.length);
        System.arraycopy(saved_salt, 0, concatenated, pass_hash.length -1, saved_salt.length);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] manual_hash = md.digest(concatenated);
        LOGGER.debug("Manual hash: " +ConversionsUtils.bytesToHex(manual_hash));

        Assertions.assertArrayEquals(manual_hash, auto_hash);


    }
}
