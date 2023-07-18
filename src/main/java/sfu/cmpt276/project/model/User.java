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
    private String accountType;
<<<<<<< HEAD
=======
    private String location;
    private String budget;
    private String startDate;
    private String endDate;
>>>>>>> devMain
    private String pin;
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
        this.pin = null;
        this.accessibility = null;
        this.dietaryRestriction = null;
        this.languageOne = null;
        this.languageTwo = null;
        this.languageThree = null;
        this.location = null;
        this.budget = null;
        this.startDate = null;
        this.endDate = null;
    }

    public void setTripPreferences(String location, String budget, String startDate, String endDate) {
        this.location = location;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setPreferences(String accessibility, String dietaryRestriction, String languageOne, String languageTwo, String languageThree) {
        this.accessibility = accessibility;
        this.dietaryRestriction = dietaryRestriction;
        this.languageOne = languageOne;
        this.languageTwo = languageTwo;
        this.languageThree = languageThree;
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
<<<<<<< HEAD

=======
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
    
>>>>>>> devMain
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
<<<<<<< HEAD
=======

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

>>>>>>> devMain
}
