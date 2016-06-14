package main.user;

import org.junit.Test;

import static main.user.Validator.*;
import static org.junit.Assert.assertFalse;

/**
 * Created by said on 17.10.15.
 */

public class ValidatorTest {

    @Test
    public void testIsValidLogin() throws Exception {
        String login = "";

        assertFalse(isValidLogin(login));
    }

    @Test
    public void testIsValidPassword() throws Exception {
        String password = "";

        assertFalse(isValidPassword(password));
    }

    @Test
    public void testIsValidEmail() throws Exception {
        String email = "test_email";

        assertFalse(isValidEmail(email));
    }
}