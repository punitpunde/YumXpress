
package yumxpress.util;

import java.util.Base64;


public class PasswordEncryption {
    public static String getEncryptedPassword(String password){
        Base64.Encoder en=Base64.getEncoder();
        return en.encodeToString(password.getBytes());
    }
    
    public static String getDecryptedPassword(String password){
        Base64.Decoder dc=Base64.getDecoder();
        byte [] decryptPwd= dc.decode(password.getBytes());
        return new String(decryptPwd);
    }
    public static void main(String[] args) {
        System.err.println(getDecryptedPassword("YWJj"));
         System.err.println(getEncryptedPassword("cHVuaXQ"));
        
    }
   
}
