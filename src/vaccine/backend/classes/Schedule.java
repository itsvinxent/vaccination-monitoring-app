package vaccine.backend.classes;

public class Schedule {

    int patientNum;
    String doctorName, patientName;
    String patientLName, patientFName, city;
    String vaccineBrand, firstDose, secondDose, firstTime, secondTime, status;

    // Constructor
    public Schedule(int patientNum, String doctorName, String patientLName, String patientFName,
                    String vaccineBrand, String firstDose, String secondDose,
                    String firstTime, String secondTime, String status, String city) {
        this.patientNum = patientNum;
        this.doctorName = doctorName;
        this.patientName = patientLName + ", " + patientFName;
        this.patientLName = patientLName;
        this.patientFName = patientFName;
        this.vaccineBrand = vaccineBrand;
        this.firstDose = firstDose;
        this.secondDose = secondDose;
        this.firstTime = firstTime;
        this.secondTime = secondTime;
        this.status = status;
        this.city = city;
    }

    public Schedule(String doctorName, String patientLName, String patientFName,
                    String vaccineBrand, String firstDose, String secondDose,
                    String firstTime, String secondTime, String status, String city) {
        this.doctorName = doctorName;
        this.patientName = patientLName + ", " + patientFName;
        this.patientLName = patientLName;
        this.patientFName = patientFName;
        this.vaccineBrand = vaccineBrand;
        this.firstDose = firstDose;
        this.secondDose = secondDose;
        this.firstTime = firstTime;
        this.secondTime = secondTime;
        this.status = status;
        this.city = city;
    }

    public Schedule() {
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

    public String getDoctorName() {
        return doctorName;
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

    public String getCity() {
        return city;
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

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public void setCity(String city) {
        this.city = city;
    }

}
