package com.franchise.Franchise.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SHA256 {
    public SHA256() {
    }

	 public static String simple(String rawPassword) {
        byte[] digest = null;
        try {
            digest = sha256WithoutSaltAndIterations(rawPassword);
        } catch (NoSuchAlgorithmException ex) {
            log.error("", ex);
        }

        return bytesToHex(digest);
    }

    private static byte[] sha256WithoutSaltAndIterations(String rawPassword) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        return digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
