/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasecurityserver;

import com.ah.authentication.Authentication;
import com.ah.security.DataEncryption;
import com.ah.security.PairKeyGenerator;
import com.ah.security.SignDataWithKey;
import com.ah.util.CompressionUtils;
import com.ah.util.FileReadWrite;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.zip.DataFormatException;
import javafx.util.Pair;

/**
 *
 * @author Md. Amran Hossain || SA || cell +8801817126345 ||
 * amrancse930@gmail.com
 */
public class JavaSecurityServer {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     * @throws java.util.zip.DataFormatException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException, DataFormatException {

        DataEncryption dataEncryption = new DataEncryption();
        SignDataWithKey signDataWithKey = new SignDataWithKey();
        

        final String strToEncrypt = "Hello Amran";
        final String strPssword = "just@1234";
        final String orgId = "0001";
        //Step-One:: encrypt user data
        Pair<String, String> encData = dataEncryption.encrypt(strToEncrypt, strPssword, orgId);
        //Step Two get publicKey value      
        byte[] publicKeyValue = CompressionUtils.compress(FileReadWrite.getPubKeyFile(orgId));
        //Step-Three Digital Sign With private key
        String signResult = signDataWithKey.dataSignWithDSAndPrivateKey(encData.getValue(), orgId);
        

        System.out.println("------------------Key Value----------------------------------");
        System.out.println("| " + CompressionUtils.compress(signResult.getBytes("UTF-8"))
                + " | " + CompressionUtils.compress(encData.getKey().getBytes("UTF-8"))
                + " | " + publicKeyValue);
        System.out.println("------------------Key Value-----------------------------------");

    }

}
