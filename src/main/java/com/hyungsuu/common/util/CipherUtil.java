package com.hyungsuu.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

//import com.dreamsecurity.magicauth.ecid.MagicAuth4ECID;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


















public class CipherUtil
{
  private static final int BUFFER_SIZE = 4096;
  private static byte[] ivBytes = new byte[16];
  
  private static final char[] passwords = { 
      'Z', 'X', 'C', 'V', 'B', 'N', 'M', 'L', 'K', 'J', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
  
  private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();








  
  public static KeyPair genRSAKeyPair(int keySize) throws NoSuchAlgorithmException {
    SecureRandom secureRandom = new SecureRandom();
    
    KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
    gen.initialize(keySize, secureRandom);
    return gen.genKeyPair();
  }















  
  public static String encryptRSA(String plainText, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(1, publicKey);
    byte[] bytePlain = cipher.doFinal(plainText.getBytes("UTF-8"));




    
    return new String(Base64.encodeBase64(bytePlain));
  }




  
  public static String encryptRSA(String plainText, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(1, privateKey);
    byte[] bytePlain = cipher.doFinal(plainText.getBytes("UTF-8"));

    
    return new String(Base64.encodeBase64(bytePlain));
  }
















  
  public static String decryptRSA(String encrypted, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
    Cipher cipher = Cipher.getInstance("RSA");

    
    byte[] byteEncrypted = Base64.decodeBase64(encrypted.getBytes());
    cipher.init(2, privateKey);
    byte[] bytePlain = cipher.doFinal(byteEncrypted);
    return new String(bytePlain, "utf-8");
  }


  public static String decryptRSA(String encrypted, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
    Cipher cipher = Cipher.getInstance("RSA");
    
    byte[] byteEncrypted = Base64.decodeBase64(encrypted.getBytes());
    cipher.init(2, publicKey);
    byte[] bytePlain = cipher.doFinal(byteEncrypted);
    return new String(bytePlain, "utf-8");
  }




  
  public static String sign(String plainText, PrivateKey privateKey)
			throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException {
		
		Signature privateSignature = Signature.getInstance("RSA");
		privateSignature.initSign(privateKey);
		privateSignature.update(plainText.getBytes("UTF-8"));
		byte[] signature = privateSignature.sign();
		
//		return Base64.getEncoder().encodeToString(signature);
//		return Base64.encodeBase64String(signature);
		return new String(Base64.encodeBase64(signature));
	}


  
  public static PublicKey convertStringToPublicKey(String strPublicKey) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
    KeyFactory kf = null;
    kf = KeyFactory.getInstance("RSA");
    
    return kf.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(strPublicKey.getBytes())));
  }



  
  public static PrivateKey convertStringToPrivateKey(String strPrivateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
    KeyFactory kf = null;
    kf = KeyFactory.getInstance("RSA");
    
    return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(strPrivateKey.getBytes())));
  }





  
  public static String hashMacSha256(String text, byte[] secretKey) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
    Key sk = new SecretKeySpec(secretKey, "HmacSHA256");
    Mac mac = Mac.getInstance(sk.getAlgorithm());
    mac.init(sk);
    byte[] hmac = mac.doFinal(text.getBytes("UTF-8"));
    
    return new String(Base64.encodeBase64(hmac));
  }



  
  public static byte[] hashSha256(String msg) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(msg.getBytes("UTF-8"));
    return md.digest();
  }


  
  public static byte[] hashSha256(byte[] msg) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(msg);
    return md.digest();
  }


  
  public static byte[] hashSha384(byte[] msg) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md = MessageDigest.getInstance("SHA-384");
    md.update(msg);
    return md.digest();
  }


  
  public static boolean verifyHmacHash(String body, String stringPublicKey, HashMap<String, String> map) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
    boolean result = false;
    PublicKey publicKey = convertStringToPublicKey(stringPublicKey);
    String hashValue = null;
    if (body != null) {
      hashValue = hashMacSha256(body, publicKey.getEncoded());
      if (hashValue.equals(map.get("apiKeyHashcd"))) {
        result = true;
      }
    } else {
      result = true;
    } 
    return result;
  }


  
  public static String getHmacHashValue(String body, String stringPublicKey) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IllegalStateException, UnsupportedEncodingException  {
		
		String hashValue = null;
		hashValue= CipherUtil.hashMacSha256(body,stringPublicKey.getBytes());
	    
	    return hashValue;
	}

  
  public static String createRandomPasswords(int length) {
    long currentTime = System.currentTimeMillis();
    StringBuilder sb = new StringBuilder("");
    Random r = new Random(currentTime);
    for (int i = 0; i < length; i++) {
      sb.append(passwords[r.nextInt(passwords.length)]);
    }
    return sb.toString();
  }



  
  public static String createOpenKey() { 
	  return createRandomPasswords(32);
  }








  
  private static String bytesToHex(byte[] a) {
		StringBuilder sb = new StringBuilder(a.length * 2);
		for(byte b: a)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}



  /**
   * 일반 문자열을 지정된 키를 이용하여 AES256 으로 암호화
   * @param  String - 암호화 대상 문자열
   * @param  String - 문자열 암호화에 사용될 키
   * @return String - key 로 암호화된  문자열 
   * @exception 
   */
  public static String encodeByAES256(String str, String key) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
   
   byte[] textBytes = str.getBytes("UTF-8");
   AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
   SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
   Cipher cipher = null;
   cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
   cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
   return new String(Base64.encodeBase64(cipher.doFinal(textBytes)));
  }






  
  /**
   * 암호화된 문자열을 지정된 키를 이용하여 AES256 으로 복호화
   * @param  String - 복호화 대상 문자열
   * @param  String - 문자열 복호화에 사용될 키
   * @return String - key 로 복호화된  문자열 
   * @exception 
   */ 
  public static String decodeByAES256(String str, String key) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
   
	byte[] textBytes = Base64.decodeBase64(str.getBytes());
   AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
   SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
   Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
   cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
   return new String(cipher.doFinal(textBytes), "UTF-8");
  }



  @Deprecated
  public static HashMap<String, String> makeReqHeaderMap(String apiKeyHashcd, String apiUtlinsttCode, String apiUtlinsttTrnsmisNo, String docDecKey, String deviceID) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    HashMap<String, String> reqHeaderMap = new HashMap<String, String>();
    if (apiKeyHashcd != null) {
      reqHeaderMap.put("apiKeyHashcd", apiKeyHashcd);
    }
    
    if (apiUtlinsttCode != null) {
      reqHeaderMap.put("apiUtlinsttCode", apiUtlinsttCode);
    }
    
    if (apiUtlinsttTrnsmisNo != null) {
      reqHeaderMap.put("apiUtlinsttTrnsmisNo", apiUtlinsttTrnsmisNo);
    }
    
    if (docDecKey != null) {
      reqHeaderMap.put("docDecKey", docDecKey);
    }
    
    if (deviceID != null) {
      reqHeaderMap.put("deviceID", deviceID);
    }
    
    return reqHeaderMap;
  }


	public static long getCurrentTimewithMillis() {
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}	   




  
  public static String bin2str(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0xF];
    } 
    return new String(hexChars);
  }
  
  public static byte[] str2bin(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = 
        (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }


  
  public static byte[] copyToByteArray(InputStream in) throws IOException {
    if (in == null) {
      return new byte[0];
    }
    
    ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
    copy(in, out);
    return out.toByteArray();
  }

  
  public static int copy(InputStream in, OutputStream out) throws IOException {
    int byteCount = 0;
    byte[] buffer = new byte[4096];
    int bytesRead = -1;
    while ((bytesRead = in.read(buffer)) != -1) {
      out.write(buffer, 0, bytesRead);
      byteCount += bytesRead;
    } 
    out.flush();
    return byteCount;
  }


  
  public static String copyToString(InputStream in, Charset charset) throws IOException {
    if (in == null) {
      return "";
    }
    
    StringBuilder out = new StringBuilder();
    InputStreamReader reader = new InputStreamReader(in, charset);
    char[] buffer = new char[4096];
    int bytesRead = -1;
    while ((bytesRead = reader.read(buffer)) != -1) {
      out.append(buffer, 0, bytesRead);
    }
    return out.toString();
  }



	public static HashMap<String, Object> convertJsonToMap(String body) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = (HashMap<String, Object>) mapper.readValue(body, new TypeReference<Map<String, Object>>(){});
		
        return map;
	}

  
//  public static String encAria(byte[] encData, byte[] keys) throws Exception {
//    String result_string = null;
//    try {
//      byte[] apiKeyShaHash = hashSha384(keys);
//      byte[] result = MagicAuth4ECID.encrypt(apiKeyShaHash, encData);
//
//      result_string = new String(Base64.encodeBase64(result));
//      
////      System.out.println("암호화 성공=>" + result_string);
//    } catch (Exception e) {
//      throw new Exception("암호화 실퍠!!");
//    } 
//
//    
//    return result_string;
//  }
//
//  
//  
//  public static byte[] encByteAria(byte[] encData, byte[] keys) throws Exception {
//	  	byte[] result = null;
//	    try {
//	      byte[] apiKeyShaHash = hashSha384(keys);
//	      result = MagicAuth4ECID.encrypt(apiKeyShaHash, encData);
//
//	    } catch (Exception e) {
//	      throw new Exception("암호화 실퍠!!");
//	    } 
//
//	    
//	    return result;
//	  }
//
//
//  
//  public static byte[] decAria(byte[] decData, byte[] keys) throws Exception {
//    byte[] result = null;
//    
//    try {
//      byte[] apiKeyShaHash = hashSha384(keys);
//      result = MagicAuth4ECID.decrypt(apiKeyShaHash, Base64.decodeBase64(decData));
//      String result_string = (new BigInteger(1, result)).toString(16);
////      System.out.println("복호화 성공 =>");
//    } catch (Exception e) {
//      throw new Exception("복호화 실패!!");
//    } 
//    
//    return result;
//  }
}
