package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.engineering.others.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.model.MedicalOffice;

public class MedicalOfficeMapper implements BeanAndModelMapper<MedicalOfficeBean, MedicalOffice> {

    @Override
    public MedicalOffice fromBeanToModel(MedicalOfficeBean bean) {
        return new MedicalOffice(bean.getPsychologist(), bean.getCity(), bean.getPostCode(), bean.getAddress(), bean.getOtherInfo());
    }

    @Override
    public MedicalOfficeBean fromModelToBean(MedicalOffice model) {
        return new MedicalOfficeBean(model.getPsychologist(), model.getCity(), model.getPostCode(), model.getAddress(), model.getOtherInfo());
    }
}
