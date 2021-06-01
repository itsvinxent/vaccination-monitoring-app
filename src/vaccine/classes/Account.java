package vaccine.classes;

public class Account {
    int userID;
    String username, password, usertype;

    public Account(int userID, String username, String password, String usertype) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.usertype = usertype;
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
}
