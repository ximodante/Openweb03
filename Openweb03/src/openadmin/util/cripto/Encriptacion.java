package openadmin.util.cripto;

import java.security.MessageDigest;
import java.util.Base64;

//import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class Encriptacion {

	
	public static String md5(String pText) throws Exception  {
		
		String result = "";
		
		MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
		
		md.update(pText.getBytes());
	    
		byte[] digest = md.digest();
		
		/**
		 for (byte b : digest) {
	       
			 result = result + Integer.toHexString(0xFF & b);
	      
	       	 System.out.println("Encriptacio MD5 Hexadecimal: " + result);
		
		 }*/
		 
		 //byte[] encoded = Base64.encodeBase64(digest);
		 
		 //System.out.println("Encriptacio MD5 Base 64: " + new String(encoded));
		 
		 System.out.println("Encriptacio MD5 Base 64 java: " +  new String(Base64.getEncoder().encodeToString(digest)));
		 result = new String(new String(Base64.getEncoder().encodeToString(digest)));
		 
		return result;
	}
	
	
	
}
