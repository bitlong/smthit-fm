package com.smthit.framework.dal.hibernate.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.smthit.framework.dal.enums.SmthitEnumType;

@Table(name = "tb_demo")
@Entity
public class DemoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
	 *  映射数据中的枚举类型
	 */
	@Column(name = "smthitType", nullable = false, length = 1)
	@Type(type="com.smthit.framework.dal.enums.SmthitEnumType",
	    parameters={@Parameter(name="enumClass",value="com.smthit.framework.dal.enums.SmthitEnumType")})
	private SmthitEnumType smthitType;
	
	
	public DemoEntity() {
	}

}