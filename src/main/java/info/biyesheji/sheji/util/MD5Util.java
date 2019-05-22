package info.biyesheji.sheji.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class MD5Util {

    private static Cipher cipher = null;
    private static PBEParameterSpec pbeParameterSpec = null;
    private static Key key = null;

    static {
        //初始化盐
        SecureRandom random = new SecureRandom();
        byte[] salt = random.generateSeed(8);   //指定为8位的盐 （盐就是干扰码，通过添加干扰码增加安全）
        //口令和密钥
        String password = "sha_bi_bao_li_po_jie";              //口令
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            key = factory.generateSecret(pbeKeySpec);  //密钥
            //加密   参数规范，第一个参数是盐，第二个是迭代次数（经过散列函数多次迭代）
            pbeParameterSpec = new PBEParameterSpec(salt, 100);
            cipher = Cipher.getInstance("PBEWITHMD5andDES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    //加密
    public static final String encrypt(String src) {
        byte[] result = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
            result = cipher.doFinal(src.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(result);
    }

    //解密
    public static final String decrypt(String src) {
        byte[] bytes = Base64.decodeBase64(src);
        byte[] result = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
            result = cipher.doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return new String(result);
    }

}
