package com.theradiary.ispwtheradiary.engineering.others;

import com.theradiary.ispwtheradiary.controller.application.Search;
import com.theradiary.ispwtheradiary.controller.graphic.*;
import com.theradiary.ispwtheradiary.controller.graphic.account.AccountController;
import com.theradiary.ispwtheradiary.controller.graphic.account.PatientAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.account.PsychologistAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepageController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePsController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePtController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.controller.graphic.login.PatientRegistrationController;
import com.theradiary.ispwtheradiary.controller.graphic.login.PsychologistRegistrationController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPatientController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPsychologistController;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {
    private final Session session;

    public ControllerFactory(Session session) {
        this.session = session;
    }

    @Override
    public Object call(Class<?> type) {
        System.out.println(type.getSimpleName());
        return switch (type.getSimpleName()) {
            case "PsychologistAccountController" -> new PsychologistAccountController(session);
            case "PatientAccountController" -> new PatientAccountController(session);
            case "HomepageController" -> new HomepageController(session);
            case "HomepagePtController" -> new HomepagePtController(session);
            case "HomepagePsController" -> new HomepagePsController(session);
            case "LoginController" -> new LoginController(session);
            case "PatientRegistrationController" -> new PatientRegistrationController(session);
            case "PsychologistRegistrationController" -> new PsychologistRegistrationController(session);
            case "ModifyPatientController" -> new ModifyPatientController(session);
            case "ModifyPsychologistController" -> new ModifyPsychologistController(session);
           // case "AppointmentController" -> new AppointmentController(session);
            //case "DashboardController" -> new DashboardController(session);
            //case "DiaryAndTasksController" -> new DiaryAndTasksController(session);
           // case "MedicalOfficeController" -> new MedicalOfficeController(session);
            //case "PsychologistListController" -> new PsychologistListController(session);
            case "SearchController" -> new SearchController(session);
            case "PatientListController" -> new PatientListController(session);
            case "AccountController" -> null;
            case "CommonController" -> null;
            default -> throw new IllegalArgumentException("Controller non supportato: " + type);
        };
    }
}