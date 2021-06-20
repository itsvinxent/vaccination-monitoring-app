package vaccine.backend.classes;

public class Vaccine {
    int vaccineID, storageAmount, doseInterval;;
    String vaccineBrand;

    public Vaccine(int vaccineID, String vaccineBrand, int storageAmount, int doseInterval) {
        this.vaccineID = vaccineID;
        this.vaccineBrand = vaccineBrand;
        this.storageAmount = storageAmount;
        this.doseInterval = doseInterval;
    }

    public Vaccine(String vaccineBrand, int storageAmount, int doseInterval) {
        this.vaccineBrand = vaccineBrand;
        this.storageAmount = storageAmount;
        this.doseInterval = doseInterval;
    }


    public int getVaccineID() {
        return vaccineID;
    }

    public String getVaccineBrand() {
        return vaccineBrand;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public int getDoseInterval() {
        return doseInterval;
    }

    public void setVaccineID(int vaccineID) {
        this.vaccineID = vaccineID;
    }

    public void setVaccineBrand(String vaccineBrand) {
        this.vaccineBrand = vaccineBrand;
    }

    public void setStorageAmount(int storageAmount) {
        this.storageAmount = storageAmount;
    }

    public void setDoseInterval(int doseInterval) {
        this.doseInterval = doseInterval;
    }
}
