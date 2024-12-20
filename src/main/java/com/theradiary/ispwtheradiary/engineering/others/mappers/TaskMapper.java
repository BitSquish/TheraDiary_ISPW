package com.theradiary.ispwtheradiary.engineering.others.mappers;

import com.theradiary.ispwtheradiary.engineering.others.beans.TaskBean;
import com.theradiary.ispwtheradiary.model.Task;

public class TaskMapper implements BeanAndModelMapper<TaskBean, Task> {
    @Override
    public Task fromBeanToModel(TaskBean bean) {
        return new Task(bean.getTaskName(), bean.getTaskDeadline(), bean.getTaskStatus());
    }

    @Override
    public TaskBean fromModelToBean(Task model) {
        return new TaskBean(model.getTaskName(), model.getTaskDeadline(), model.getTaskStatus());
    }
}
