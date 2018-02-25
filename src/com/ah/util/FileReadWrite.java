/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ah.util;

import com.ah.constraint.KeyFileName;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Md. Amran Hossain || SAt || cell +8801817126345 ||
 * amrancse930@gmail.com
 */
public class FileReadWrite implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(FileReadWrite.class);

    public static Integer keyValueWrite(final PrivateKey privateKey, final PublicKey publicKey, final String orgId) {
        String workingDir = new StringBuilder(System.getProperty("user.dir")).toString();//For Devlopment
        //String workingDir = new StringBuilder(System.getProperty("catalina.base")).toString();//For Server
        //Create Dir name "keyDir" if does't exists
        File dirCreate = new File(workingDir + "/keyDir");//Folder Name keyDir
        File fileCreate;
        FileOutputStream fos;
        ObjectOutputStream objectOutputStream;
        try {
            if (!dirCreate.exists()) {
                dirCreate.mkdir();
                dirCreate.setReadable(true, true);
                dirCreate.setWritable(true, true);
            }
            //Initialize keyFile "keyFile" if does't exists
            fileCreate = new File(dirCreate.getAbsolutePath() + "/" + orgId + KeyFileName.PUBPRVKEYFILE);
            if (orgId != null) {
                if (!fileCreate.exists()) {
                    fileCreate.createNewFile();
                    fileCreate.setReadable(true, true);
                    fileCreate.setWritable(true, true);
                } else {
                    fileCreate.delete();
                    fileCreate.createNewFile();
                    fileCreate.setReadable(true, true);
                    fileCreate.setWritable(true, true);
                }
            }
            //Now we are write public key and private key value store in the file
            //Where we can used next time.
            fos = new FileOutputStream(new File(dirCreate.getAbsolutePath() + "/" + orgId + KeyFileName.PUBPRVKEYFILE));
            objectOutputStream = new ObjectOutputStream(fos);

            //Data add in the object
            Pair<String, Object> prvKey = new Pair<>("privateKey", privateKey);
            Pair<String, Object> pubKey = new Pair<>("publicKey", publicKey);

            //private and public key write to the file.
            objectOutputStream.writeObject(prvKey);
            objectOutputStream.writeObject(pubKey);

            //After complete task it should be close
            fos.close();
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            LOGGER.info("File not found\t" + e.getMessage());
            return StatusCode.FAILED;//Bad Ststus
        } catch (IOException e) {
            LOGGER.info("Error initializing stream\t" + e.getMessage());
            return StatusCode.FAILED;//Bad Ststus
        }
        LOGGER.info("Key pair write success !");
        return StatusCode.SUCCESS; //Success Status
    }

    public static List<Pair<String, Object>> keyValueRead(final String orgId) {
        FileInputStream fis;
        ObjectInputStream objectInputStream;
        List<Pair<String, Object>> pairs = new ArrayList<>();
        String workingDir = new StringBuilder(System.getProperty("user.dir")).toString();//For Devlopment
        //String workingDir = new StringBuilder(System.getProperty("catalina.base")).toString();//For Server
        //Create Dir name "keyDir" if does't exists
        File dirCreate = new File(workingDir + "/keyDir");//Folder Name keyDir
        try {
            if (orgId != null) {
                fis = new FileInputStream(new File(dirCreate.getAbsolutePath() + "/" + orgId + KeyFileName.PUBPRVKEYFILE));
                objectInputStream = new ObjectInputStream(fis);
                // Read objects
                Pair<String, Object> pubKey = (Pair<String, Object>) objectInputStream.readObject();
                Pair<String, Object> prvKey = (Pair<String, Object>) objectInputStream.readObject();
                pairs.add(pubKey);
                pairs.add(prvKey);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReadWrite.class.getName()).log(Level.ALL, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileReadWrite.class.getName()).log(Level.ALL, null, ex);
        }
        return pairs;
    }

    public static Integer privateKeyValueWrite(final PrivateKey privateKey, final String orgId) {
        String workingDir = new StringBuilder(System.getProperty("user.dir")).toString();//For Devlopment
        //String workingDir = new StringBuilder(System.getProperty("catalina.base")).toString();//For Server
        //Create Dir name "keyDir" if does't exists
        File dirCreate = new File(workingDir + "/keyDir");//Folder Name keyDir
        File fileCreate;
        FileOutputStream fos;
        ObjectOutputStream objectOutputStream;
        try {
            if (!dirCreate.exists()) {
                dirCreate.mkdir();
                dirCreate.setReadable(true, true);
                dirCreate.setWritable(true, true);
            }
            //Initialize keyFile "keyFile" if does't exists
            fileCreate = new File(dirCreate.getAbsolutePath() + "/" + orgId + KeyFileName.PRIV_KEY_FILE);
            if (orgId != null) {
                if (!fileCreate.exists()) {
                    fileCreate.createNewFile();
                    fileCreate.setReadable(true, true);
                    fileCreate.setWritable(true, true);
                } else {
                    fileCreate.delete();
                    fileCreate.createNewFile();
                    fileCreate.setReadable(true, true);
                    fileCreate.setWritable(true, true);
                }
            }
            //Now we are write public key and private key value store in the file
            //Where we can used next time.
            fos = new FileOutputStream(new File(dirCreate.getAbsolutePath() + "/" + orgId + KeyFileName.PRIV_KEY_FILE));
            objectOutputStream = new ObjectOutputStream(fos);

            //private key write to the file.
            objectOutputStream.writeObject(privateKey.getEncoded());

            //After complete task it should be close
            fos.close();
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            LOGGER.info("File not found\t" + e.getMessage());
            return StatusCode.FAILED;//Bad Ststus
        } catch (IOException e) {
            LOGGER.info("Error initializing stream\t" + e.getMessage());
            return StatusCode.FAILED;//Bad Ststus
        }
        LOGGER.info("Private key write success !");
        return StatusCode.SUCCESS; //Success Status
    }

    public static Integer publicKeyValueWrite(final PublicKey publicKey, final String orgId) {
        String workingDir = new StringBuilder(System.getProperty("user.dir")).toString();//For Devlopment
        //String workingDir = new StringBuilder(System.getProperty("catalina.base")).toString();//For Server
        //Create Dir name "keyDir" if does't exists
        File dirCreate = new File(workingDir + "/keyDir");//Folder Name keyDir
        File fileCreate;
        FileOutputStream fos;
        ObjectOutputStream objectOutputStream;
        try {
            if (!dirCreate.exists()) {
                dirCreate.mkdir();
                dirCreate.setReadable(true, true);
                dirCreate.setWritable(true, true);
            }
            //Initialize keyFile "keyFile" if does't exists
            fileCreate = new File(dirCreate.getAbsolutePath() + "/" + orgId + KeyFileName.PUB_KEY_FILE);
            if (orgId != null) {
                if (!fileCreate.exists()) {
                    fileCreate.createNewFile();
                    fileCreate.setReadable(true, true);
                    fileCreate.setWritable(true, true);
                } else {
                    fileCreate.delete();
                    fileCreate.createNewFile();
                    fileCreate.setReadable(true, true);
                    fileCreate.setWritable(true, true);
                }
            }
            //Now we are write public key and private key value store in the file
            //Where we can used next time.
            fos = new FileOutputStream(new File(dirCreate.getAbsolutePath() + "/" + orgId + KeyFileName.PUB_KEY_FILE));
            objectOutputStream = new ObjectOutputStream(fos);

            //private key write to the file.
            objectOutputStream.writeObject(publicKey.getEncoded());

            //After complete task it should be close
            fos.close();
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            LOGGER.info("File not found\t" + e.getMessage());
            return StatusCode.FAILED;//Bad Ststus
        } catch (IOException e) {
            LOGGER.info("Error initializing stream\t" + e.getMessage());
            return StatusCode.FAILED;//Bad Ststus
        }
        LOGGER.info("Public key write success !");
        return StatusCode.SUCCESS; //Success Status
    }

    public static byte[] privKeyFileRead(final String orgId) {
        FileInputStream fis;
        ObjectInputStream objectInputStream;
        byte[] keyValue = null;
        String workingDir = new StringBuilder(System.getProperty("user.dir")).toString();//For Devlopment
        //String workingDir = new StringBuilder(System.getProperty("catalina.base")).toString();//For Server
        //Create Dir name "keyDir" if does't exists
        File dirCreate = new File(workingDir + "/keyDir");//Folder Name keyDir
        try {
            if (orgId != null) {
                fis = new FileInputStream(new File(dirCreate.getAbsolutePath() + "/" + orgId + KeyFileName.PRIV_KEY_FILE));
                objectInputStream = new ObjectInputStream(fis);
                // Read objects
                keyValue = (byte[]) objectInputStream.readObject();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReadWrite.class.getName()).log(Level.ALL, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileReadWrite.class.getName()).log(Level.ALL, null, ex);
        }
        return keyValue;
    }

    public static Integer signValueWrite(final byte[] signValue, final String orgId) {
        String workingDir = new StringBuilder(System.getProperty("user.dir")).toString();//For Devlopment
        //String workingDir = new StringBuilder(System.getProperty("catalina.base")).toString();//For Server
        //Create Dir name "keyDir" if does't exists
        File dirCreate = new File(workingDir + "/keyDir");//Folder Name keyDir
        File fileCreate;
        FileOutputStream fos;
        ObjectOutputStream objectOutputStream;
        try {
            if (!dirCreate.exists()) {
                dirCreate.mkdir();
                dirCreate.setReadable(true, true);
                dirCreate.setWritable(true, true);
            }
            //Initialize keyFile "keyFile" if does't exists
            fileCreate = new File(dirCreate.getAbsolutePath() + "/" + orgId + KeyFileName.DIGITAL_SIGN_FILE);
            if (orgId != null) {
                if (!fileCreate.exists()) {
                    fileCreate.createNewFile();
                    fileCreate.setReadable(true, true);
                    fileCreate.setWritable(true, true);
                } else {
                    fileCreate.delete();
                    fileCreate.createNewFile();
                    fileCreate.setReadable(true, true);
                    fileCreate.setWritable(true, true);
                }
            }
            //Now we are write public key and private key value store in the file
            //Where we can used next time.
            fos = new FileOutputStream(new File(dirCreate.getAbsolutePath() + "/" + orgId + KeyFileName.DIGITAL_SIGN_FILE));
            objectOutputStream = new ObjectOutputStream(fos);

            //private key write to the file.
            objectOutputStream.writeObject(signValue);

            //After complete task it should be close
            fos.close();
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            LOGGER.info("File not found\t" + e.getMessage());
            return StatusCode.FAILED;//Bad Ststus
        } catch (IOException e) {
            LOGGER.info("Error initializing stream\t" + e.getMessage());
            return StatusCode.FAILED;//Bad Ststus
        }
        LOGGER.info("Public key write success !");
        return StatusCode.SUCCESS; //Success Status
    }

    public static byte[] getPubKeyFile(String orgId) {
        String workingDir = new StringBuilder(System.getProperty("user.dir")).toString();//For Devlopment
        //String workingDir = new StringBuilder(System.getProperty("catalina.base")).toString();//For Server
        File directory = new File(workingDir + "/keyDir");//Folder Name keyDir
        byte[] publicKey = null;
        FileInputStream fis;
        ObjectInputStream objectInputStream;
        try {
            fis = new FileInputStream(new File(directory.getAbsolutePath() + "/" + orgId + KeyFileName.PUB_KEY_FILE));
            objectInputStream = new ObjectInputStream(fis);
            // Read objects
            publicKey = (byte[]) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.info("Error reading stream\t" + e.getMessage());
        }
        return publicKey;
    }
}
