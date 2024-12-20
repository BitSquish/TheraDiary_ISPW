package com.theradiary.ispwtheradiary.engineering.others.mappers;

public interface BeanAndModelMapper<B, M> {
    M fromBeanToModel(B bean); // Da Bean a Model
    B fromModelToBean(M model); // Da Model a Bean
}
