/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ah.security;

import com.ah.constraint.CipherAlgorithm;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javafx.util.Pair;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;

/**
 *
 * @author Md. Amran Hossain || SA || cell +8801817126345 ||
 * amrancse930@gmail.com
 */
public class DataEncryption implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(KeyGenerator.class);

    private static String encryptedString;
    private final KeyGenerator keyGenerator;
    private SecretKeySpec secretKeySpec;

    public DataEncryption() {
        keyGenerator = new KeyGenerator();
    }

    public Pair<String, String> encrypt(String strToEncrypt, String encPassword, String orgId) {
        Pair<String, String> pair;
        try {
            Cipher cipher = Cipher.getInstance(CipherAlgorithm.AES_ECB_PKCS5PADDING);
            if (encPassword != null) {
                secretKeySpec = keyGenerator.generateKey(encPassword);
                keyGenerator.generatePubPrivKey(orgId);
            } else {
                LOGGER.info("DataEncryption::encrypt() can't null keyValue!");
                return null;
            }
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            setEncryptedString(Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOGGER.info("DataEncryption::encrypt() Error while encrypting: " + e.toString());
        }
        pair = new Pair<>(secretKeySpec.toString(), encryptedString);
        return pair;
    }

    /**
     * @return the encryptedString
     */
    public static String getEncryptedString() {
        return encryptedString;
    }

    /**
     * @param aEncryptedString the encryptedString to set
     */
    public static void setEncryptedString(String aEncryptedString) {
        encryptedString = aEncryptedString;
    }
}
