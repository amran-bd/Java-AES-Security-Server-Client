/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ah.security;

import com.ah.constraint.HashAlgorithm;
import com.ah.util.FileReadWrite;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Md. Amran Hossain || SAt || cell +8801817126345 ||
 * amrancse930@gmail.com
 */
public class SignDataWithKey implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(SignDataWithKey.class);

    public SignDataWithKey() {
    }

    public String dataSignWithDSAndPrivateKey(String DSData, String orgId) {
        try {
            LOGGER.info("SignDataWithKey::dataSignWithDSAndPrivateKey() method call...");

            /* Read the private key bytes */
            byte[] bytes = FileReadWrite.privKeyFileRead(orgId);
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance(HashAlgorithm.HASH_RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
            Signature sign = Signature.getInstance(HashAlgorithm.HASH_SHA256_WITH_RSA);
            sign.initSign(privateKey);
            byte[] buf = new byte[2048];
            int length = DSData.length();
            sign.update(buf, 0, length);
            byte[] signature = sign.sign();
            return signature.toString();
            //Integer fileWriteStatus =  fileReadWrite.signValueWrite(signature, orgId);
//            if (fileWriteStatus == 200) {
//                LOGGER.info("Sign file create success !");
//                return Boolean.TRUE;
//            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException ex) {
            Logger.getLogger(SignDataWithKey.class.getName()).log(Level.ALL, null, ex);
        }
        return null;
    }

}
