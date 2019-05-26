package com.smthit.task.dal.entity;

import java.util.Date;

import org.beetl.sql.core.annotatoin.UpdateIgnore;

import lombok.Data;

@Data
public class BaseEntity {
	private Integer status;
	
	@UpdateIgnore
	private Date createdAt;
	private Date updateAt;
	
}
