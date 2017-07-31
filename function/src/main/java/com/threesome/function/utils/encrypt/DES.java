package com.threesome.function.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * author: L.K.X
 * created on: 2017/7/18 下午5:12
 * description:
 *
 *  DES密钥长度是56位，
 */

public class DES {


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
        return null;
    }


    /**
     * DESCBC加密
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回加密后的数据
     * @throws Exception
     */
    public String encryptDESCBC(final String src, final String key) throws Exception {

        // --生成key,同时制定是des还是DESede,两者的key长度要求不同
        final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // --加密向量
        final IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

        // --通过Chipher执行加密得到的是一个byte的数组,Cipher.getInstance("DES")就是采用ECB模式,cipher.init(Cipher.ENCRYPT_MODE,
        // secretKey)就可以了.
        final Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        final byte[] b = cipher.doFinal(src.getBytes("UTF-8"));

        return Base64.encode(b);
    }

    /**
     * DESCBC解密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回解密后的原始数据
     * @throws Exception
     */
    public String decryptDESCBC(final String src, final String key) throws Exception {
//        // --通过base64,将字符串转成byte数组
        final byte[] bytesrc = Base64.decode(src);

        // --解密的key
        final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // --向量
        final IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

        // --Chipher对象解密Cipher.getInstance("DES")就是采用ECB模式,cipher.init(Cipher.DECRYPT_MODE,
        // secretKey)就可以了.
        final Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        final byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);

    }

}
