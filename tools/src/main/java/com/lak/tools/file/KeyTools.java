package com.lak.tools.file;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lawrence
 */

public class KeyTools {

    public static String hashKeyForDisk(String key) {
        String hashKey = "";
        if (!TextUtils.isEmpty(key)) {
            try {
                final MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(key.getBytes());
                hashKey = bytesToHexString(digest.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                hashKey = String.valueOf(key.hashCode());
            }
        }
        return hashKey;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1)
                stringBuilder.append("0");
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

}
