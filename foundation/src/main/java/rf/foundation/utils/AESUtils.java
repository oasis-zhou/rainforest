package rf.foundation.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @ClassName AESUtils
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/10
 * @Version V1.0
 **/
public class AESUtils {

    /**
     * AES加密算法-加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        //构造秘钥生成器
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(key.getBytes());
        kgen.init(128, secureRandom);
        //产生原始对称密钥
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        //生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
        //AES加密算法
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = data.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] result = cipher.doFinal(byteContent);
        //base64处理
        return new BASE64Encoder().encode(result);
    }

    /**
     * AES加密算法-解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {
        byte[] buf = new BASE64Decoder().decodeBuffer(data); //base64处理

        //构造秘钥生成器
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(key.getBytes());
        kgen.init(128, secureRandom);
        //产生原始对称密钥
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        //生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
        //AES加密算法
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] result = cipher.doFinal(buf);
        //base64处理
        return new String(result);
    }
}
