package com.theradiary.ispwtheradiary.beans;


public abstract class LoggedUserBean {
    private CredentialsBean credentialsBean;
    private String name;
    private String surname;
    private String city;
    private String description;
    private boolean inPerson;
    private boolean online;
    private boolean pag;
    private static final String IN_PERSON_AND_ONLINE = "In presenza e online";
    private static final String IN_PERSON = "In presenza";
    private static final String ONLINE_MODALITY = "Online";


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
    public String getSurname() {
        return surname;
    }
    public String getCity() {
        return city;
    }
    public String getDescription() {
        return description;
    }
    public boolean isInPerson() {
        return inPerson;
    }
    public boolean isOnline() {
        return online;
    }
    public boolean isPag() {
        return pag;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setInPerson(boolean inPerson) {
        this.inPerson = inPerson;
    }
    public void setOnline(boolean online) {
        this.online = online;
    }
    public void setPag(boolean pag) {
        this.pag = pag;
    }
    public String getFullName(){
        return name + " " + surname;
    }
    public boolean getInPersonFromModality(String modality){
        return modality.equals(IN_PERSON_AND_ONLINE) || modality.equals(IN_PERSON);
    }
    public boolean getOnlineFromModality(String modality){
        return modality.equals(IN_PERSON_AND_ONLINE) || modality.equals(ONLINE_MODALITY);
    }
    public String getModality() {
        String modality = "";
        if (this.isInPerson() && this.isOnline()) {
            modality += IN_PERSON_AND_ONLINE;
        } else if (this.isInPerson()) {
            modality += IN_PERSON;
        } else {
            modality += ONLINE_MODALITY;
        }
        return modality;
    }




}
