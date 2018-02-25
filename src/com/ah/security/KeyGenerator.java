/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ah.security;

import com.ah.constraint.AlgorithmSize;
import com.ah.constraint.CipherAlgorithm;
import com.ah.constraint.HashAlgorithm;
import com.ah.util.FileReadWrite;
import com.ah.util.StatusCode;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Md. Amran Hossain || SAt || cell +8801817126345 ||
 * amrancse930@gmail.com
 */
public class KeyGenerator implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(KeyGenerator.class);

    private byte[] key;

    public KeyGenerator() {
    }

    //This method serve encryption process.
    public SecretKeySpec generateKey(final String encPassword) {
        //Digest for secretkey
        MessageDigest messageDigest = null;
        SecretKeySpec secretKey = null;
        try {
            key = encPassword.getBytes("UTF-8");
            messageDigest = MessageDigest.getInstance(HashAlgorithm.HASH_SHA256);
            key = messageDigest.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit
            secretKey = new SecretKeySpec(key, CipherAlgorithm.AES);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.info("Generate key problem!" + e.getMessage());
        }
        return secretKey;
    }

    //This method serve private key and public key for Digital Signatuer 
    public Integer generatePubPrivKey(final String orgId) {
        try {
            java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance(HashAlgorithm.HASH_RSA);
            /* initialize with keySize: typically 2048 for RSA */
            keyPairGenerator.initialize(AlgorithmSize.HASH_2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            Integer privKeyWriteResult = FileReadWrite.privateKeyValueWrite(keyPair.getPrivate(), orgId);
            Integer pubKeyWriteResult = FileReadWrite.publicKeyValueWrite(keyPair.getPublic(), orgId);
            if (privKeyWriteResult == 200 && pubKeyWriteResult == 200) {
                LOGGER.info("Private key write success!");
                return StatusCode.SUCCESS;
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyGenerator.class.getName()).log(Level.ALL, null, ex);
        }
        return StatusCode.FAILED;
    }

}
