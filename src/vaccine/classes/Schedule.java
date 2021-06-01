package vaccine.classes;

public class Schedule {

    int patientNum, doctorNum;
    String patientName;
    String patientLName, patientFName;
    String vaccineBrand, firstDose, secondDose, firstTime, secondTime, status;

    // Constructor
    public Schedule(int patientNum, int doctorNum, String patientLName, String patientFName,
                    String vaccineBrand, String firstDose, String secondDose,
                    String firstTime, String secondTime,String status) {
        this.patientNum = patientNum;
        this.doctorNum = doctorNum;
        this.patientName = patientLName + ", " + patientFName;
        this.patientLName = patientLName;
        this.patientFName = patientFName;
        this.vaccineBrand = vaccineBrand;
        this.firstDose = firstDose;
        this.secondDose = secondDose;
        this.firstTime = firstTime;
        this.secondTime = secondTime;
        this.status = status;
    }

    public String getPatientLName() {
        return patientLName;
    }

    public String getPatientFName() {
        return patientFName;
    }

    public String getPatientName() {
        return patientName;
    }

    public int getPatientNum() {
        return patientNum;
    }

    public int getDoctorNum() {
        return doctorNum;
    }

    public String getVaccineBrand() {
        return vaccineBrand;
    }

    public String getFirstDose() {
        return firstDose;
    }

    public String getSecondDose() {
        return secondDose;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public String getSecondTime() {
        return secondTime;
    }

    public String getStatus() {
        return status;
    }


    public void setPatientLName(String patientLName) {
        this.patientLName = patientLName;
    }

    public void setPatientFName(String patientFName) {
        this.patientFName = patientFName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientNum(int patientNum) {
        this.patientNum = patientNum;
    }

    public void setDoctorNum(int doctorNum) {
        this.doctorNum = doctorNum;
    }

    public void setVaccineBrand(String vaccineBrand) {
        this.vaccineBrand = vaccineBrand;
    }

    public void setFirstDose(String firstDose) {
        this.firstDose = firstDose;
    }

    public void setSecondDose(String secondDose) {
        this.secondDose = secondDose;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public void setSecondTime(String secondTime) {
        this.secondTime = secondTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
