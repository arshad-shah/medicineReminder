package com.example.medicinereminder.helperClasses;

/**
 * MecicineReminder class
 * this class is used to store the medicine details
 * @author Arshad Shah
 */
public class MedicineReminder {

    private int reminderId;
    private String medicineName;
    private String dosesPerDay;
    private String numberOfDay;
    
    public MedicineReminder(int reminderId, String medicineName, String dosesPerDay, String numberOfDay) {
        setReminderId(reminderId);
        setMedicineName(medicineName);
        setDosesPerDay(dosesPerDay);
        setNumberOfDay(numberOfDay);
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
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

    public String getNumberOfDay() {
        return numberOfDay;
    }

    public void setNumberOfDay(String numberOfDay) {
        this.numberOfDay = numberOfDay;
    }
}
