package kr.nanoit.old.client;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class ClientCrypt {

    // NOTE: STATIC
    public static final String AES_CBC_PKCS_5_PADDING = "AES/CBC/PKCS5PADDING";
    public static final String ALGORITHM_AES = "AES";
    public static final int KEY_MAX_LENGTH = 16;

    // NOTE: CONSTRUCTOR
    // NOTE: class variable
    private Key key;
    private IvParameterSpec ivParameterSpec;



    public void initialize(String enckey) {
        // 초기화

        byte[] slicedKey = getBytesKeyByLength(enckey);
        byte[] IV = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        this.ivParameterSpec = new IvParameterSpec(IV);
        this.key = new SecretKeySpec(slicedKey, ALGORITHM_AES);

    }

    private byte[] getBytesKeyByLength(String enckey) {

        byte[] byteKey = enckey.getBytes(StandardCharsets.UTF_8);
        if (byteKey.length < KEY_MAX_LENGTH) {
            return byteKey;
        } else {
            return Arrays.copyOfRange(byteKey, 0, KEY_MAX_LENGTH);
        }
    }

    /**
     * 비밀번호 암호화
     */

    public String DataEncrypt(String pw, String enckey) throws Exception {
        initialize(enckey);
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(pw.getBytes(StandardCharsets.UTF_8)));

    }

    public String MessageDataEncrypt(String data, String enckey) throws Exception {

        initialize(enckey);
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));

    }

}