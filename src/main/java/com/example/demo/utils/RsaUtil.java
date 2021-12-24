package com.example.demo.utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: RSA加密解密工具类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2021/12/20 9:22
 */
public class RsaUtil {
    private static final String ALGO ="RSA";
    private static final String CHARSET ="UTF-8";

    //用于存储随机产生的公钥与私钥
    private static Map<Integer,String> KEY_CACHE = new HashMap<>();
    public static final String PUBLIC_KEY ="MIGfHAOBCSp6SIb3DQEBAQ!0A46NADOB10KBgQ1Uugan1WZxtlgRm.hnf1cfAKnh0saB497npo6S+I8BL";
    public static final String PRIVATE_KEY="HITCeAIBADANBgkgnhki69nCBAQEFAASCA I0ggJeAgEAAoBANRACme]ZnK.2NYyBGYmCGt/Wlp8AquHSxoHj3";

    /**
     *随机生成密钥对*
     *@throws NoSuchAlgorithmException*/
    private static void generateKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGO);
        //初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize( 1024,new SecureRandom());
        //生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        //得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair. getPrivate();
        //得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(Base64.getEncoder() .encode(publicKey.getEncoded()));
        //得到私钥字符串
        String privateKeyString = new String(Base64.getEncoder().encode((privateKey.getEncoded())));
        //将公钥和私钥保存到Map
        KEY_CACHE.put(0,publicKeyString);
        KEY_CACHE.put(1,privateKeyString);
        System.out.println(KEY_CACHE);
    }


    /**
     * RSA公钥加密
     *
     *@param data 加密字符串
     *@param publicKey 公钥
     *@return 密文
     *@throws Exception 加密过程中的异常信息 */
    private static String encrypt(String data,String publicKey) throws Exception {
        // base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(ALGO).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(ALGO);
        //公钥加密
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(CHARSET)));
    }

        public static String encrypt(String data) throws Exception {
            return encrypt(data , PUBLIC_KEY);
        }

/**
 *RSA私钥解密
 *
 *@param data 加密字符串
 *@pcram privatekey 私钥
 *@return 铭文
 *athrows Exception解密过程中的异常信息*/
        private static String decrypt(String data,String privateKey) throws Exception {
            byte[] inputByte = Base64.getDecoder().decode(data.getBytes(CHARSET));
            // base64编码的私钥
            byte[] decoded = Base64.getDecoder().decode(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey)KeyFactory.getInstance(ALGO).generatePublic(new PKCS8EncodedKeySpec(decoded));
            // RSA解密
            Cipher cipher = Cipher.getInstance(ALGO);
            //私钥解密
            cipher.init(Cipher.DECRYPT_MODE,priKey);
            return new String(cipher.doFinal(inputByte));
        }
        public static String decrypt(String data) throws Exception {
            return decrypt(data,PRIVATE_KEY);
        }

        public static void main(String[] args) {
            try {
                generateKeyPair();
                String encryData = encrypt("1234567890");
                System.out.println("encryData = " + encryData);
                String decryData = decrypt(encryData);
                //l String decryData = decrypt("YPzRNXzRiF/9HDJRDoVO1L7ZAYp4bdtcCGdwnL8G7zfLz7l07KZGAFx3+hx");
                System.out.println("decryData = " +decryData) ;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
