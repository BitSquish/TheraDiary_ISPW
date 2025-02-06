package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.beans.ToDoItemBean;
import com.theradiary.ispwtheradiary.model.ToDoItem;

public class ToDoItemMapper implements BeanAndModelMapper<ToDoItemBean, ToDoItem> {
    @Override
    public ToDoItem fromBeanToModel(ToDoItemBean bean) {
        return new ToDoItem(bean.getToDo(), bean.isCompleted());
    }

    @Override
    public ToDoItemBean fromModelToBean(ToDoItem model) {
        return new ToDoItemBean(model.getToDo(), model.isCompleted());
    }
}
