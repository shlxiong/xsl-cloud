package com.openxsl.studycloud.feign.invoker.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Assert;

public class AESPasswordEncoder {
	static final String KEY_ALGORITHM = "AES";
	static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	static SecretKeySpec keySpec;
	
	public void setSalt(String salt) {
		Assert.notNull(salt, "加密盐不能为空");
		while (salt.length() < 16) {
			salt += salt;
		}
		salt = salt.substring(0, 16);
		keySpec = new SecretKeySpec(salt.getBytes(), KEY_ALGORITHM);
	}

//	@Override
	public String encode(CharSequence rawPassword) {
		return this.encode(rawPassword.toString());
	}

//	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return this.encode(rawPassword.toString()).equals(encodedPassword);
	}

//	@Override
	public String encode(String password) {
		try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encoded = cipher.doFinal(password.getBytes());
            return HexEncoder.encode(encoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}

//	@Override
	public String decode(String encoded) {
		try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] original = cipher.doFinal(HexEncoder.decodeBytes(encoded));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
	}
	
}
