package com.example.medicinereminder.helperClasses;

public class MedicineReminder {

    private int id;
    private String medicineName;
    private String dosesPerDay;
    private String numberOfDays;

    public MedicineReminder( int id, String medicineName, String dosesPerDay, String numberOfDays) {
        setId(id);
        setMedicineName(medicineName);
        setDosesPerDay(dosesPerDay);
        setNumberOfDays(numberOfDays);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosesPerDay() {
        return dosesPerDay;
    }

    public void setDosesPerDay(String dosesPerDay) {
        this.dosesPerDay = dosesPerDay;
    }

    public String getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(String numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
