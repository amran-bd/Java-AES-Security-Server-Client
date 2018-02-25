/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ah.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.zip.Deflater;
import org.apache.log4j.Logger;

/**
 *
 * @author Md. Amran Hossain || SA || cell +8801817126345 ||
 * amrancse930@gmail.com
 */
public class CompressionUtils implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(CompressionUtils.class);

    public CompressionUtils() {
    }

    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[2048];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index  
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        LOGGER.info("Compression sucess!");
        return output;
    }

}
