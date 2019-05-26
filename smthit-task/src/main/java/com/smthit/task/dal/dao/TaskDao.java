package com.smthit.task.dal.dao;

import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import com.smthit.task.dal.entity.TaskPO;

@SqlResource("smith.task.Task")
public interface TaskDao extends BaseMapper<TaskPO> {
}
