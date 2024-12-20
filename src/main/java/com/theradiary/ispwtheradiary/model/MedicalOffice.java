package com.theradiary.ispwtheradiary.model;

public class MedicalOffice {
    private String psychologist;
    private String postCode;
    private String address;
    private String city;
    private String otherInfo;

    public MedicalOffice (String psychologist, String city, String postCode, String address, String otherInfo){
        this.psychologist = psychologist;
        this.postCode = postCode;
        this.address = address;
        this.city = city;
        this.otherInfo = otherInfo;
    }

    public String getPsychologist() {
        return psychologist;
    }

    public void setPsychologist(String psychologist) {
        this.psychologist = psychologist;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}

