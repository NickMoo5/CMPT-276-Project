package sfu.cmpt276.project.model;

public class AddUser {
    private String fName;
    private String lName;
    private String email;
    private String username; 

    public AddUser(String fName, String lName, String email, String username) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.username = username;
    }

    public AddUser(){
        this.fName = "";
        this.lName = "";
        this.email = "";
        this.username = "";
    }

    public String getfName() {
        return fName;
    }
    public void setfName(String fName) {
        this.fName = fName;
    }
    public String getlName() {
        return lName;
    }
    public void setlName(String lName) {
        this.lName = lName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
