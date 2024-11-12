package com.theradiary.ispwtheradiary.model.beans;

import com.theradiary.ispwtheradiary.engineering.enums.Category;

import java.util.ArrayList;
import java.util.List;

public class PatientBean extends LoggedUserBean{
    private ArrayList<Category> categories;
    private PsychologistBean psychologistBean;
    public PatientBean(CredentialsBean credentialsBean, String name, String surname, String city, String description, boolean inPerson, boolean online, boolean pag, List<Category> categories,PsychologistBean psychologistBean) {
        super(credentialsBean, name, surname, city, description, inPerson, online, pag);
        this.categories = (categories != null) ? (ArrayList<Category>) categories : new ArrayList<>();
        this.psychologistBean = psychologistBean;
    }
    public PsychologistBean getPsychologistBean(){
        return psychologistBean;
    }

    public void setPsychologistBean(PsychologistBean psychologistBean) {
        this.psychologistBean = psychologistBean;
    }

    public List<Category> getCategories() {
        return categories;
    }
    public void addCategory(Category category){
        categories.add(category);
    }

    public void removeCategory(Category category){
        categories.remove(category);
    }
    public void setCategories(List<Category> categories) {
        this.categories = (ArrayList<Category>) categories;
    }
}

