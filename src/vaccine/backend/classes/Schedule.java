package vaccine.backend.classes;

public class Schedule {

    int patientNum;
    String doctorName_1, doctorName_2;
    String patientName, patientLName, patientFName, city, age, sex;
    String vaccineBrand, firstDose, secondDose, firstTime, secondTime, status;

    // Constructor
    public Schedule(int patientNum, String doctorName_1, String doctorName_2,
                    String patientLName, String patientFName, String age, String sex, String city,
                    String vaccineBrand, String firstDose, String secondDose,
                    String firstTime, String secondTime, String status) {
        this.patientNum = patientNum;
        this.doctorName_1 = doctorName_1;
        this.doctorName_2 = doctorName_2;
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
        this.age = age;
        this.sex = sex;
    }

    public Schedule(String doctorName_1, String doctorName_2,
                    String patientLName, String patientFName, String age, String sex, String city,
                    String vaccineBrand, String firstDose, String secondDose,
                    String firstTime, String secondTime, String status) {
        this.doctorName_1 = doctorName_1;
        this.doctorName_2 = doctorName_2;
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
        this.age = age;
        this.sex = sex;
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

    public String getDoctorName_1() {
        return doctorName_1;
    }

    public String getDoctorName_2() {
        return doctorName_2;
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

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
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

    public void setDoctorName_1(String doctorName_1) {
        this.doctorName_1 = doctorName_1;
    }

    public void setDoctorName_2(String doctorName_2) {
        this.doctorName_2 = doctorName_2;
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

    public void setAge(String age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
