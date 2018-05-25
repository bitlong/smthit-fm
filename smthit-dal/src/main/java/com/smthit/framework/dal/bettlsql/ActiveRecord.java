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

	default void updateStamp() {}
	
	default void createStamp() {}
	
    default boolean save() {
    		//
    		createStamp();
        return $().insertTemplate(this) > 0;
    }

    default boolean saveDo() {
    		//
    		createStamp();	
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
