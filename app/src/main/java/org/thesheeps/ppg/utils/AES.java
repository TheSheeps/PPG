package org.thesheeps.ppg.utils;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES encryption
 */

public final class AES {

    private static final String LOGTAG = "PPG_AES";
    private static final byte[] ivBytes = {0x2d, 0x7a, 0x15, 0x0f, 0x1e, 0x34, 0x00, 0x6b, 0x1f,
            0x38, 0x0a, 0x02, 0x2a, 0x37, 0x4d, 0x00};

    public static String encrypt(final String password, String message)
            throws GeneralSecurityException {

        String encoded = null;

        try {
            final SecretKeySpec key = generateKey(password);

            byte[] cipherText = encrypting(key, ivBytes, message.getBytes("UTF-8"));

            encoded = Base64.encodeToString(cipherText, Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            Log.e(LOGTAG, "Unsupported Encoding: ", e);
        }

        return encoded;
    }

    public static byte[] encrypting(final SecretKeySpec key, final byte[] iv, final byte[] message)
            throws GeneralSecurityException {

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        return cipher.doFinal(message);
    }

    public static String decrypt(final String password, String base64EncodedCipherText)
            throws GeneralSecurityException {

        String message = null;

        try {
            final SecretKeySpec key = generateKey(password);

            byte[] decodedCipherText = Base64.decode(base64EncodedCipherText, Base64.NO_WRAP);

            byte[] decryptedBytes = decrypting(key, ivBytes, decodedCipherText);

            message = new String(decryptedBytes, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            Log.e(LOGTAG, "Unsupported Encoding: ", e);
        }

        return message;
    }

    public static byte[] decrypting(final SecretKeySpec key, final byte[] iv, final byte[] decodedCipherText)
            throws GeneralSecurityException {

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        return cipher.doFinal(decodedCipherText);
    }

    private static SecretKeySpec generateKey(final String password) throws
            NoSuchAlgorithmException, UnsupportedEncodingException {

        final MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] bytes = password.getBytes("UTF-8");

        digest.update(bytes, 0, bytes.length);

        byte[] key = digest.digest();

        return new SecretKeySpec(key, "AES");
    }
}