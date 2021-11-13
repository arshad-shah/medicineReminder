package com.example.medicinereminder.ObjectClasses;

/**
 * MecicineReminder class
 * this class is used to store the medicine details
 * @author Arshad Shah
 */
public class MedicineReminder {
    /**
     * reminderId
     */
    private int reminderId;

    /**
     * medicineName
     */
    private String medicineName;

    /**
     * dosesPerDay
     */
    private String dosesPerDay;

    /**
     * numberOfDay
     */
    private String numberOfDay;

    /**
    *The constructor of the MedicineReminder.
    * @param reminderId the reminderId
    * @param medicineName the medicineName
    * @param dosesPerDay the dosesPerDay
    * @param numberOfDay the numberOfDay
    */
    public MedicineReminder(int reminderId, String medicineName, String dosesPerDay, String numberOfDay) {
        this.reminderId = reminderId;
        this.medicineName = medicineName;
        this.dosesPerDay = dosesPerDay;
        this.numberOfDay = numberOfDay;
    }


    /**
     * getter for reminderId
     * @return reminderId
     */
    public int getReminderId() {
        return reminderId;
    }

    /**
     * setter for reminderId
     * @param reminderId    the reminderId
     */
    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    /**
     * getter for medicineName
     * @return medicineName
     */
    public String getMedicineName() {
        return medicineName;
    }


    /**
     * setter for medicineName
     * @param medicineName  the medicineName
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * getter for dosesPerDay
     * @return dosesPerDay
     */
    public String getDosesPerDay() {
        return dosesPerDay;
    }

    /**
     * setter for dosesPerDay
     * @param dosesPerDay   the dosesPerDay
     */
    public void setDosesPerDay(String dosesPerDay) {
        this.dosesPerDay = dosesPerDay;
    }

    /**
     * getter for numberOfDay
     * @return numberOfDay
     */
    public String getNumberOfDay() {
        return numberOfDay;
    }

    /**
     * setter for numberOfDay
     * @param numberOfDay   the numberOfDay
     */
    public void setNumberOfDay(String numberOfDay) {
        this.numberOfDay = numberOfDay;
    }
}
