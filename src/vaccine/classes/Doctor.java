package vaccine.classes;

public class Doctor {
    int doctorNum, schedule;
    String doctorName;

    public Doctor(int doctorNum, String doctorName, int schedule) {
        this.doctorNum = doctorNum;
        this.doctorName = doctorName;
        this.schedule = schedule;
    }

    public int getDoctorNum() {
        return doctorNum;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setDoctorNum(int doctorNum) {
        this.doctorNum = doctorNum;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }
}
