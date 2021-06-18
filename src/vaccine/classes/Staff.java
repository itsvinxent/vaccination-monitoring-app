package vaccine.classes;

public class Staff {
    int staffID;
    String staffName;

    public Staff(int staffID, String staffName) {
        this.staffID = staffID;
        this.staffName = staffName;
    }

    public int getStaffID() {
        return staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
