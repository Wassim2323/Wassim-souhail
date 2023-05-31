package com.example.memoireversionfinale.viemodele;

public class userdata {
    private static String Name;
    private String Email;
    private static String Contact;
    private static String Address;
    private static int Gender;
    private static int BloodGroup;
    private static int Division;

    public userdata() {

    }

    public static String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public static String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public static int getDivision() {
        return Division;
    }

    public void setDivision(int division) {
        this.Division = division;
    }

    public static String getName() {
        return Name;
    }

    public static int getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(int bloodGroup) {
        this.BloodGroup = bloodGroup;
    }

    public String getEmail() {
        return Email;
    }

    public static int getGender() {
        return Gender;
    }

    public void setName(String name) { this.Name = name; }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setGender(int gender) {
        this.Gender = gender;
    }

}
