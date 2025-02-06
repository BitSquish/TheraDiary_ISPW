package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.beans.*;
import com.theradiary.ispwtheradiary.engineering.others.beans.*;
import com.theradiary.ispwtheradiary.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.model.*;

public class MapperRegistration {
    //Questo metodo viene chiamato nel main per registrare tutti i mappers

    private MapperRegistration() {
    }
    public static void registerMappers() {
        BeanAndModelMapperFactory factory = BeanAndModelMapperFactory.getInstance();
        factory.registerMapper(PatientBean.class, Patient.class, new PatientMapper());
        factory.registerMapper(PsychologistBean.class, Psychologist.class, new PsychologistMapper());
        factory.registerMapper(CredentialsBean.class, Credentials.class, new CredentialsMapper());
        factory.registerMapper(AppointmentBean.class, Appointment.class, new AppointmentMapper());
        factory.registerMapper(MedicalOfficeBean.class, MedicalOffice.class, new MedicalOfficeMapper());
        factory.registerMapper(RequestBean.class, Request.class, new RequestMapper());
        factory.registerMapper(TaskBean.class, Task.class, new TaskMapper());
        factory.registerMapper(ToDoItemBean.class, ToDoItem.class, new ToDoItemMapper());
    }
}
