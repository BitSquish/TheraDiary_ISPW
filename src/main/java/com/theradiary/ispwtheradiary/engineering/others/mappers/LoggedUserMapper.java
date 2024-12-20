package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.LoggedUser;
//TODO Vedere se può essere utile
public abstract class LoggedUserMapper implements BeanAndModelMapper<LoggedUserBean, LoggedUser> {
    protected final CredentialsMapper credentialsMapper = new CredentialsMapper();
    @Override
    public LoggedUser fromBeanToModel(LoggedUserBean bean) {
        return mapToModel(bean);
    }

    @Override
    public LoggedUserBean fromModelToBean(LoggedUser model) {
        return mapToBean(model);
    }

    // Metodi da implementare nelle sottoclassi concrete
    protected abstract LoggedUser mapToModel(LoggedUserBean bean);
    protected abstract LoggedUserBean mapToBean(LoggedUser model);
}
