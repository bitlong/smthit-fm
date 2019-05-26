/**
 * 
 */
package com.smthit.task.dal.dao;

import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import com.smthit.task.dal.entity.TaskLogPO;

/**
 * @author Bean
 *
 */
@SqlResource("smith.task.TaskHistory")
public interface TaskLogDao extends BaseMapper<TaskLogPO>{
}
