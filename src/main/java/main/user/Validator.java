package main.user;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by said on 14.10.15.
 */
public class Validator {
    private static Pattern loginRegex = Pattern.compile("^\\w{4,20}$");
    private static Pattern passwordRegex = Pattern.compile("^\\w{6,20}$");

    public static boolean isValidLogin(String login) {
        Matcher matcher = loginRegex.matcher(login);

        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        Matcher matcher = passwordRegex.matcher(password);

        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
