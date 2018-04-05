/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import static com.smthit.framework.dal.bettlsql.SqlKit.$;

/**
 * @author Bean
 *
 */
public interface ActiveRecord {

	default void updateStamp() {
		//do nothing;
	}
	
    default boolean save() {
    		//
    		updateStamp();
        return $().insertTemplate(this) > 0;
    }

    default boolean saveDo() {
    		//
    		updateStamp();	
        return $().insertTemplate(this, true) > 0;
    }

    
    default boolean update() {
    		//
    		updateStamp();
        return $().updateTemplateById(this) > 0;
    }

    default boolean delete() {
    		//
        return $().deleteObject(this) > 0;
    }
}
