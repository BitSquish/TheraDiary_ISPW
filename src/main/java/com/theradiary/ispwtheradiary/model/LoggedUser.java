package com.theradiary.ispwtheradiary.model;

public abstract class LoggedUser {
    private Credentials credentials;
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

    protected LoggedUser(Credentials credentials, String name, String surname, String city, String description, boolean isInPerson, boolean isOnline) {
        this.credentials = credentials;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.description = description;
        this.inPerson = isInPerson;
        this.online = isOnline;
        this.pag = false;
    }
    protected  LoggedUser(Credentials credentials){
        this.credentials = credentials;
        this.name = null;
        this.surname = null;
        this.city = null;
        this.description = null;
        this.inPerson = false;
        this.online = false;
        this.pag = false;
    }
    //getter e setter per i nuovi campi

    public Credentials getCredentials() {
        return credentials;
    }

    //FORSE DA TOGLIERE
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
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

    public  void setPag(boolean pag) {
        this.pag = pag;
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

    public boolean getInPersonFromModality(String modality){
        return modality.equals(IN_PERSON_AND_ONLINE) || modality.equals(IN_PERSON);
    }

    public boolean getOnlineFromModality(String modality){
        return modality.equals(IN_PERSON_AND_ONLINE) || modality.equals(ONLINE_MODALITY);
    }


}

