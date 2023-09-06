package com.gomeals.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserSecurity {

    private static final String ENCRYPTION_KEY = "gomealsproject77";
    SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
    Cipher cipher;

    {
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptData(String plainText) {
        String encryptedText = null;
        if (plainText != null) {
            try {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
            byte[] encryptedBytes = new byte[0];
            try {
                encryptedBytes = cipher.doFinal(plainText.getBytes());
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            }
            encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            return encryptedText;
        } else {
            System.out.println("Plaintext cannot be null");
        }
        return encryptedText;
    }

    public String decryptData(String cipherText) {
        String decryptedText = null;
        if (cipherText != null) {
            try {
                cipher.init(Cipher.DECRYPT_MODE, key);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
            byte[] decryptedBytes = new byte[0];
            try {
                decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            }
            decryptedText = new String(decryptedBytes);
            return decryptedText;
        } else {
            System.out.println("Decrypted text cannot be null");
        }
        return decryptedText;

    }

    public static void main(String[] args) {
        UserSecurity obj = new UserSecurity();
        String cipher = obj.encryptData("thewall");
        System.out.println(cipher);
        System.out.println("Decrypted text:" + obj.decryptData(cipher));
    }
}
