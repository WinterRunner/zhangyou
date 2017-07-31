package com.threesome.function.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * author: L.K.X
 * created on: 2017/7/18 上午11:00
 * description:
 * <p>
 * <p>
 * DES密钥长度是56位，3DES加长了密钥长度，可以为112位或168位
 */

public class DES3 {

    //初始化生成密钥

    public static String getKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();
            return Hex.encodeHex(secretKey.getEncoded(),false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 3DESECB加密,key必须是长度大于等于 3*8 = 24 位哈
    public static String encode(final String src, final String key){
        try {
            final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            final SecretKey securekey = keyFactory.generateSecret(dks);

            final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            final byte[] b = cipher.doFinal(src.getBytes());

            return Base64.encode(b);
        }catch (Exception e){
            e.printStackTrace();
        }
       return "";
    }

    // 3DESECB解密,key必须是长度大于等于 3*8 = 24 位哈
    public static String decode(final String src, final String key){
        try {
            // --解密的key
            final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            final SecretKey securekey = keyFactory.generateSecret(dks);

            // --Chipher对象解密
            final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            final byte[] retByte = cipher.doFinal(Base64.decode(src));

            return new String(retByte,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
