package org.paladin.utils;

import java.security.Key;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;



public class DesEncrypt {

	public static final String DES_PUBLIC_ENCRYPT_KEY = "6Ta4OaHZdpA="; //DES加密key 
	public static final String DES_PRIVATE_ENCRYPT_KEY ="o0al4OaEWBzA1";
	public static final String UTF8 = "UTF-8";
	Key key;
	
	private static final Map<String,String> replaceStr = new HashMap<String,String>();
	
	static {
		replaceStr.put("\\+", "\\[j]"); 
		replaceStr.put("/", "\\[x]");
		replaceStr.put("=", "\\[d]");
	}

	public DesEncrypt() {
		setKey(DES_PRIVATE_ENCRYPT_KEY);
	}

	public DesEncrypt(String str) {
		setKey(str);// 生成密匙
	}
	/**
	 * 替换密文中的特殊字符
	 * @param str
	 * @return
	 */
	public String replaceKeyByValue(String str){
		Iterator<Entry<String, String>> entrys = replaceStr.entrySet().iterator();
		while(entrys.hasNext()){
			Entry<String, String> entry = entrys.next();
			str = str.replaceAll(entry.getKey(), entry.getValue());
		}
		return str;
	}
	/**
	 * 还原密文中的特殊字符
	 * @param str
	 * @return
	 */
	public String replaceValueByKey(String str){
		Iterator<Entry<String, String>> entrys = replaceStr.entrySet().iterator();
		while(entrys.hasNext()){
			Entry<String, String> entry = entrys.next();
			str = str.replaceAll(entry.getValue(), entry.getKey());
		}
		return str;
	}

	/**
	 * 根据参数生成KEY
	 */
	public void setKey(String strKey) {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			this.key = keyFactory.generateSecret(new DESKeySpec(strKey
					.getBytes("UTF8")));
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		}
	}

	/**
	 * 加密list中的某个值，加密后的值key=e+原来的key
	 * 
	 * @param list
	 * @param keys
	 */
	@SuppressWarnings("unchecked")
	public void encryptInList(List<Map> list, Object[] keys) {
		for (Map m : list) {
			for (Object key : keys) {
				if (m.containsKey(key)) {
					Object val = m.get(key);
					String param = "";
					if (val instanceof String) {
						param = (String) val;
					} else if (val instanceof Integer) {
						param = String.valueOf((Integer) val);
					} else if (val instanceof Long) {
						param = String.valueOf((Long) val);
					}
					String encKey = "e" + key;
					m.put(encKey, encrypt(param));
//					System.out.println("加密前：" + key + "=[" + param + "],加密后："
//							+ encKey + "=[" + m.get(encKey) + "]");
				}
			}
		}
	}

	/**
	 * 加密String明文输入,String密文输出
	 */
	public String encrypt(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		try {
			byteMing = strMing.getBytes(UTF8);
			byteMi = this.getEncCode(byteMing);
			strMi = Base64.encodeToString(byteMi);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			byteMing = null;
			byteMi = null;
		}
		return replaceKeyByValue(strMi);
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public String decrypt(String strMi) {
		strMi = replaceValueByKey(strMi);
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = Base64.encodeToByte(strMi);
			byteMing = this.getDesCode(byteMi);
			strMing = new String(byteMing, UTF8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key,
					SecureRandom.getInstance("SHA1PRNG"));
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key,
					SecureRandom.getInstance("SHA1PRNG"));
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}
	public static void main(String args[]) {
		DesEncrypt des = new DesEncrypt(DES_PUBLIC_ENCRYPT_KEY);

		String str1 = "123456";
		// DES加密
		String str2 = des.encrypt(str1);
		DesEncrypt des1 = new DesEncrypt(DES_PUBLIC_ENCRYPT_KEY);
		String deStr = des1.decrypt(str2);
		System.out.println("密文:" + str2);
		// DES解密
		System.out.println("明文:" + deStr);
		DesEncrypt des2  = new DesEncrypt(DES_PRIVATE_ENCRYPT_KEY);
		
		String b = des2.encrypt(deStr);
		System.out.println(b);
		String replaced = new DesEncrypt().replaceKeyByValue(b);
		System.out.println(replaced);
		System.out.println(new DesEncrypt().replaceKeyByValue("Z8/eB+qfBLQ="));
		System.out.println(new DesEncrypt().replaceValueByKey(new DesEncrypt().replaceKeyByValue("Z8/eB+qfBLQ=")));

	}
}
