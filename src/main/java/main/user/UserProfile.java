package main.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by said on 13.09.2014.
 */

@Entity
@Table(name = "Users")
public class UserProfile implements Serializable {
    @Id
    @Column(name = "idUser")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "score")
    private int score;

    public UserProfile() {
        this.id = -1;
        this.login = null;
        this.password = null;
        this.email = null;
        this.score = 0;
    }

    public UserProfile(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.score = 0;
        this.id = -1;
    }

    public UserProfile(long id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.score = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore() {
        this.score++;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
