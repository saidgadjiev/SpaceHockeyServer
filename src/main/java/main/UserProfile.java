package main;

import org.jetbrains.annotations.Nullable;

/**
 * Created by said on 13.09.2014.
 */
public class UserProfile {
    private String login;
    private String password;
    private String email;

    public UserProfile(@Nullable String login, @Nullable String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    @Nullable
    public String getLogin() {
        return login;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    @Nullable
    public String getEmail() {
        return email;
    }
}
