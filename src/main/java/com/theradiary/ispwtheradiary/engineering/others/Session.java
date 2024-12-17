package com.theradiary.ispwtheradiary.engineering.others;

import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;

public class Session {
    private LoggedUserBean user;
    private final String homepage;



    public Session() {
        this.user = null;
        this.homepage = "/com/example/theradiaryispw/logic/view/HomepageNotLogged.fxml"; //homepage con gui
    }

    public String getHomepage(){
        return this.homepage;
    }

    public void setHomepage(Role role){
        //Assegna homepage a seconda del ruolo
    }

    public LoggedUserBean getUser(){
        return this.user;
    }

    public void setUser(LoggedUserBean user){
        this.user = user;
    }


}

