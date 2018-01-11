/**
 * 
 */
package com.smthit.framework.dal.criteria;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Bean
 *
 */
@MappedSuperclass
public class EntityModel2 {
	public static final String ID = "id";
	public static final String CREATED_AT = "createdAt";
	public static final String UPDATED_AT = "updatedAt";
	

	@Id
	@GeneratedValue(generator = "hilo")
	@GenericGenerator(name = "hilo", strategy = "hilo", parameters = {
			@Parameter(name = "max_lo", value ="100"),
			@Parameter(name = "column", value = "next_value"),
			@Parameter(name = "table", value = "zeus_sequences")
	})
	private long id;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@Column
	private boolean deleted;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		EntityModel2 other = (EntityModel2) obj;
		if (id != other.id)
			return false;
		
		return true;
	}

	/**
	 * @return the delete
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param delete the delete to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
