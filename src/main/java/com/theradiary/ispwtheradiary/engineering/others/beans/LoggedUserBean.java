package com.theradiary.ispwtheradiary.engineering.others.beans;


public abstract class LoggedUserBean {
    private CredentialsBean credentialsBean;
    private String name;
    private String surname;
    private String city;
    private String description;
    private boolean inPerson;
    private boolean online;
    private boolean pag;


    protected LoggedUserBean(CredentialsBean credentialsBean, String name, String surname, String city, String description, boolean isInPerson, boolean isOnline) {
        this.credentialsBean = credentialsBean;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.description = description;
        this.inPerson = isInPerson;
        this.online = isOnline;
        this.pag = false;
    }

    //Costruttore per login
    protected LoggedUserBean(CredentialsBean credentialsBean) {
        this.credentialsBean = credentialsBean;
        this.name = "";
        this.surname = "";
        this.city = "";
        this.description = "";
        this.inPerson = false;
        this.online = false;
        this.pag = false;
    }

    public CredentialsBean getCredentialsBean() {
        return credentialsBean;
    }

    public void setCredentialsBean(CredentialsBean credentialsBean) {
        this.credentialsBean = credentialsBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInPerson() {
        return inPerson;
    }

    public void setInPerson(boolean inPerson) {
        this.inPerson = inPerson;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isPag() {
        return pag;
    }

    public void setPag(boolean pag) {
        this.pag = pag;
    }

    public String getFullName(){
        return name + " " + surname;
    }

    public String getModality() {
        String modality = "";
        if (this.isInPerson() && this.isOnline()) {
            modality += "In presenza e online";
        } else if (this.isInPerson()) {
            modality += "In presenza";
        } else {
            modality += "Online";
        }
        return modality;
    }

    public boolean getInPersonFromModality(String modality){
        return modality.equals("In presenza e online") || modality.equals("In presenza");
    }

    public boolean getOnlineFromModality(String modality){
        return modality.equals("In presenza e online") || modality.equals("Online");
    }
}
