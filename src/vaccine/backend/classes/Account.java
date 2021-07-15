package vaccine.backend.classes;

public class Account {
    int userID;
    String username, password, usertype, salt, loginstatus;

    public Account(int userID, String username, String password, String usertype, String salt) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.usertype = usertype;
        this.salt = salt;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, String usertype, String salt) {
        this.username = username;
        this.password = password;
        this.usertype = usertype;
        this.salt = salt;
    }

    public Account(int userID, String loginstatus) {
        this.userID = userID;
        this.loginstatus = loginstatus;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getSalt() {
        return salt;
    }

    public String getLoginstatus() {
        return loginstatus;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setLoginstatus(String loginstatus) {
        this.loginstatus = loginstatus;
    }
}
