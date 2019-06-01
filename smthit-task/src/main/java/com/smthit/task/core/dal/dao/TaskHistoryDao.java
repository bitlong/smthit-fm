package com.smthit.task.core.dal.dao;

import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import com.smthit.task.core.dal.entity.TaskHistoryPO;

@SqlResource("smith.task.TaskHistory")
public interface TaskHistoryDao extends BaseMapper<TaskHistoryPO>{
}
