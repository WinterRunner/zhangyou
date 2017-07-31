package com.threesome.function.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * author: L.K.X
 * created on: 2017/7/18 上午11:20
 * description:
 *
 *
 * AES密钥长度可以选择128位【16字节】，192位【24字节】和256位【32字节】密钥（其他不行，因此别乱设密码哦）。
 */

public class AES {

    public static byte[] initKey() throws Exception{

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        return secretKey.getEncoded();
    }

    public static byte[] encryptAES(byte[] data, byte[] key) throws Exception{

        SecretKey secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] resultBytes = cipher.doFinal(data);

        return resultBytes;
    }

    public static byte[] decryptAES(byte[] src, byte[] key) throws Exception{

        SecretKey secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] resultBytes = cipher.doFinal(src);

        return resultBytes;
    }
}
