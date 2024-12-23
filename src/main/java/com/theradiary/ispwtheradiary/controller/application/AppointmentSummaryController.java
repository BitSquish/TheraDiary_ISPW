package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;

public class AppointmentSummaryController {
    BeanAndModelMapperFactory beanAndModelMapperFactory;
    public AppointmentSummaryController() {
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }
    public void deleteRequest(AppointmentBean appointmentBean) {

    }
}
