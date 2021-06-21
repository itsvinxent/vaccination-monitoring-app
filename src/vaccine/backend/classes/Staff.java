package vaccine.backend.classes;

public class Staff {
    int userNum, staffID;
    String staffName;

    public Staff(int staffID, String staffName) {
        this.staffID = staffID;
        this.staffName = staffName;
    }

    public Staff(int userNum, int staffID, String staffName) {
        this.userNum = userNum;
        this.staffID = staffID;
        this.staffName = staffName;
    }

    public int getUserNum() {
        return userNum;
    }

    public int getStaffID() {
        return staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
