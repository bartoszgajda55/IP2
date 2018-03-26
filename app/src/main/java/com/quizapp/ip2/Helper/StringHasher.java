package com.quizapp.ip2.Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Aaron on 20/03/2018.
 */

public class StringHasher {

    public String hashString(String s){
        String hashed;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(s.getBytes());
            hashed = bytesToHex(messageDigest.digest());
        }catch (NoSuchAlgorithmException e){
            System.out.println("No algorithm found");
            hashed = null;
        }


        return hashed;
    }


    private String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
