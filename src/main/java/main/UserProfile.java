package main;

import org.jetbrains.annotations.Nullable;

/**
 * Created by said on 13.09.2014.
 */
public class UserProfile {
    private String login;
    private String password;
    private String email;

    public UserProfile(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }
<<<<<<< HEAD
=======

    @Nullable
>>>>>>> 8009e15106d962593b7de38c43abe693899e74a5
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
