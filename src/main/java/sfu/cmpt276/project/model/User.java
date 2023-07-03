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
    private String firstName;
    private String lastName;
    private String email;
    private String accessibility;
    private String dietaryRestriction;
    private String languageOne;
    private String languageTwo;
    private String languageThree;
    private String accountType = ACCOUNTTYPE_USER;
    public static final String ACCOUNTTYPE_USER = "user";
    public static final String ACCOUNTTYPE_ADMIN = "admin";

    public User() {}

    public User(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountType = ACCOUNTTYPE_USER;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getDietaryRestriction() {
        return dietaryRestriction;
    }

    public void setDietaryRestriction(String dietaryRestriction) {
        this.dietaryRestriction = dietaryRestriction;
    }

    public String getLanguageOne() {
        return languageOne;
    }

    public void setLanguageOne(String languageOne) {
        this.languageOne = languageOne;
    }

    public String getLanguageTwo() {
        return languageTwo;
    }

    public void setLanguageTwo(String languageTwo) {
        this.languageTwo = languageTwo;
    }

    public String getLanguageThree() {
        return languageThree;
    }

    public void setLanguageThree(String languageThree) {
        this.languageThree = languageThree;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
