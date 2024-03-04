
package yumxpress.util;

import org.apache.commons.validator.routines.EmailValidator;


public class Validator {
    public static boolean isValidEmail(String email){
        EmailValidator validator=EmailValidator.getInstance();
        return validator.isValid(email);
    }
}
