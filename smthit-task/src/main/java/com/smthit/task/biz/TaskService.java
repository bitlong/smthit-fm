/**
 * 
 */
package com.smthit.task.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smthit.task.dal.dao.TaskDao;

/**
 * @author Bean
 *
 */
@Service
@Transactional
public class TaskService {
	@Autowired
	private TaskDao taskDao;
	
	public TaskService() {
	}
}
