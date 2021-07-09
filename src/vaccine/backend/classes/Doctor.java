package vaccine.backend.classes;

public class Doctor {
    int userNum, doctorNum;
    String doctorName, schedule;

    public Doctor(int userNum, String doctorName, String schedule) {
        this.userNum = userNum;
        this.doctorName = doctorName;
        this.schedule = schedule;
    }

    public Doctor(int userNum, int doctorNum, String doctorName, String schedule) {
        this.doctorNum = doctorNum;
        this.userNum = userNum;
        this.doctorName = doctorName;
        this.schedule = schedule;
    }

    public int getUserNum() {
        return userNum;
    }

    public int getDoctorNum() {
        return doctorNum;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public void setDoctorNum(int doctorNum) {
        this.doctorNum = doctorNum;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
