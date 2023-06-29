package sfu.cmpt276.project.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String username;
    private String password;
    private String accountType = ACCOUNTTYPE_USER;

    public static final String ACCOUNTTYPE_USER = "user";
    public static final String ACCOUNTTYPE_ADMIN = "admin";

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public String getAccountType() {
        return accountType;
    }
}
