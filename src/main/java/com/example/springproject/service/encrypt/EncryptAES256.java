package com.example.springproject.service.encrypt;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

//@COnponent화 (@Controller, @Repository, @Service)와 같은 Bean으로 등록할 Class를 선언하여
//Container에서 Bean 관리와 생명주기를 처리하기 위해 @Service 등록하는 작업
@Service
public class EncryptAES256 {

    public static String alg = "AES/CBC/PKCS5Padding";
    private final String key = "012345678901234567890123456789";
    private final String iv = key.substring(0, 10);

    public String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decryted = cipher.doFinal(decodedBytes);
        return new String(decryted, "UTF-8");
    }
}
