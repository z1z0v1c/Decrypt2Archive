/*
 * This program is free software; you can redistribute it and/or
 * modify it.
 */
package files.encryptor;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/// Class used for encrypting and decrypting files in the task
///
/// @author Stefan Pantelic
/// @author Aleksandar Zizovic
public class FileEncryptor {

    public static final String INIT_VECTOR = "***Don'tPanic***"; //DO NOT REPLACE THIS VALUE!

    /// Decrypt given list of encrypted values with given key
    ///
    /// @param key       128bit long key used for decryption
    /// @param encrypted List of values to be decrypted
    /// @return list of decrypted original values
    public List<String> decrypt(String key, List<String> encrypted) {
        var originals = new ArrayList<String>();

        for (String value : encrypted) {
            String original = decrypt(key, value);
            originals.add(original);
        }

        return originals;
    }

    /// Encrypts given content with given key
    ///
    /// @param key   128bit long key used for encryption
    /// @param value Value to be encrypted
    /// @return encrypted content
    private String encrypt(String key, String value) {
        try {
            var iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            var skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            var cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException |
                 BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /// Decrypt given content with given key
    ///
    /// @param key       128bit long key used for decryption
    /// @param encrypted Value to be decrypted
    /// @return decrypted content
    private String decrypt(String key, String encrypted) {
        try {
            var iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            var skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            var cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted.trim()));

            return new String(original);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException |
                 BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
