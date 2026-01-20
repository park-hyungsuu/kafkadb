package com.hyungsuu.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import com.hyungsuu.common.exception.GlobalException;



class SR {
	private SecureRandom secureRandom;
	
    SR() {
	}
	
    void lazyInit() throws NoSuchAlgorithmException{
    	if (secureRandom != null)
    		 return;

    	secureRandom = SecureRandom.getInstance("SHA1PRNG");
    	secureRandom.setSeed(new Date().getTime());	        
    	
    }
    
	int nextInt(int bound) throws NoSuchAlgorithmException {
		lazyInit();
		return secureRandom.nextInt(bound);
	}
	
	int nextInt() throws NoSuchAlgorithmException {
		lazyInit();
		return secureRandom.nextInt();
	}
}

public class Crypto {
	static SR newSR() {
		return new com.hyungsuu.common.util.SR();
	}
    static SR sr = new SR();
	/**
	 * 핀번호를 생성한다.
	 * 
	 * @param excludePinNo
	 * @return String
	 */
	public static String createPinNo(String excludePinNo) throws GlobalException {
		try {
			if(excludePinNo == null || excludePinNo.equals("")) {
				return createRandomNo(6);
			} else {
				String pinNo = createRandomNo(6);
				// excludePinNo와 중복되지 않은 핀번호 생성
				boolean PIN_DUPLICATED = true;
				while(PIN_DUPLICATED) {
					if(pinNo.equals(excludePinNo)) {
						pinNo = createRandomNo(6);
					} else {
						PIN_DUPLICATED = false;
					}
				}
				return pinNo;
			}
		} catch (Exception e) { // 예외 발생 시
			throw new GlobalException("115", "", e); // 핀번호 생성 작업 중 오류 발생.
		} 
	}
	
	/**
	 * 핀번호를 생성한다.
	 * 
	 * @param excludePinNos
	 * @return String
	 */
	public static String createPinNo(ArrayList<String> excludePinNos) throws GlobalException {
		try {
			if(excludePinNos == null || excludePinNos.size() == 0) {
				return createRandomNo(6);
			} else {
				String pinNo = createRandomNo(6);
				// excludePinNo와 중복되지 않은 핀번호 생성
				boolean PIN_DUPLICATED = true;
				while(PIN_DUPLICATED) {
					boolean duplicated = false;
					for(String excludePinNo : excludePinNos) {
						if(pinNo.equals(excludePinNo)) {
							duplicated = true;
							break;
						}
					}
					if (duplicated == false) {
						PIN_DUPLICATED = false;
					} else {
						pinNo = createRandomNo(6);
					}
				}
				return pinNo;
			}
		} catch (Exception e) { // 예외 발생 시
			throw new GlobalException("115", "", e); // 핀번호 생성 작업 중 오류 발생.
		} 
	}
	

	
	private static final int[] PA = {9, 1, 3, 8, 7, 4, 6, 1, 2, 6, 4, 5, 1, 2, 6, 7, 9, 8}; 

	/**
	 * 주소에서 검증코드를 생성한다.
	 * 
	 * @param addressNoWithoutParity
	 * @return int
	 */
	public static int createAddressNoParity(String addressNoWithoutParity) throws GlobalException {	
		try {	
			int sum = 0;
			for(int i=0; i<addressNoWithoutParity.length(); ++i) {
				int n = Character.getNumericValue(addressNoWithoutParity.charAt(i))*PA[i];
				sum += n;
			}
			
			int p = sum%11;		
			int q = (11-p)%10;
			return q;
		} catch (Exception e) { // 예외 발생 시
			throw new GlobalException("116", "", e); 
		} 
	}
	
	/**
	 * 주소를 검증한다.
	 * 
	 * @param addressNo
	 * @return Boolean
	 */
	public static Boolean verifyAddressNo(String addressNo) throws GlobalException {	
		try {		
			int p1 = createAddressNoParity(addressNo.substring(0, addressNo.length()-2));	
			int p2 = Character.getNumericValue(addressNo.charAt(addressNo.length()-1));
			
			return p1 == p2;
		} catch (Exception e) { // 예외 발생 시
			throw new GlobalException("117", "", e);
		} 
	}
	
	private static final char[] numbers = {'0','1','2','3','4','5','6','7','8','9'};
	
	/**
	 * 난수 번호를 생성한다.
	 * 
	 * @param length
	 * @return String
	 */
	public static String createRandomNo(int length) throws GlobalException {	
		try {
	        StringBuilder sb = new StringBuilder("");
	        
	        for(int i=0; i<length; i++){
	            sb.append(numbers[sr.nextInt(numbers.length)]);
	        }
	        return sb.toString();
		} catch (Exception e) { // 예외 발생 시
			throw new GlobalException("118", "", e); // 난수 생성 작업 중 오류 발생.
		} 
	}
	

	
	private static final char[] passwords = {'Z','X','C','V','B','N','M','L','K', 'J', 'q','w','e','r','t','y','u','i','o', 'p', '!','@','#','$','%','^','&','*','(', ')', '0','1','2','3','4','5','6','7','8','9'};
	
	/**
	 * 패스워드를 생성한다.
	 * 
	 * @param length
	 * @return String
	 */
	public static String createRandomPasswords(int length) throws GlobalException {	
		try {
	        StringBuilder sb = new StringBuilder("");
	        for(int i=0; i<length; i++){
	            sb.append(passwords[sr.nextInt(passwords.length)]);
	        }
	        return sb.toString();
		} catch (Exception e) { // 예외 발생 시
			throw new GlobalException("122", "", e); // 패스워드 생성 작업 중 오류 발생.
		} 
	}
	
	/**
	 * 열람키(AES256 암복호화용)를 생성한다.
	 * 
	 * @return String
	 */
	public static String createOpenKey() throws GlobalException {
		try {
			return createRandomPasswords(32);

		} catch (Exception e) { // 예외 발생 시
			throw new GlobalException("123", "", e); // 열람키 생성 작업 중 오류 발생.
		} 
	}
	
	/**
	 * 전문번호를 생성한다.
	 * 
	 * @return String
	 */
	public static String createTransmissNo(String insttCode, String serialNumber) throws GlobalException {
		try {
			if (insttCode.length() != 3) {
				throw new GlobalException("124", ""); // 전문번호 생성 작업 중 오류 발생.
			}
			if (serialNumber.length() != 7) {
				throw new GlobalException("124", ""); // 전문번호 생성 작업 중 오류 발생.
			}
			
			SimpleDateFormat format = new SimpleDateFormat ( "yyMMdd");
			return insttCode + format.format(System.currentTimeMillis()) + serialNumber;

		} catch (Exception e) { // 예외 발생 시
			throw new GlobalException("124", "", e); // 전문번호 생성 작업 중 오류 발생.
		} 
	}
	
//	public static void main(String[] args) throws InvalidKeySpecException, GlobalException, NoSuchAlgorithmException {
//		String ah = toAddressHash("1123456789012345678");
//		System.out.print("addresss hash = "+ah+"\r\n");
//		Boolean r = verifyAddressHash(ah);
//		System.out.print(r);
//	}
}
