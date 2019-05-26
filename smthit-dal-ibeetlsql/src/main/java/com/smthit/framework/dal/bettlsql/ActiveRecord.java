/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import static com.smthit.framework.dal.bettlsql.SqlKit.$;

/**
 * @author Bean
 */
public interface ActiveRecord {

	default void updateStamp() {}
	
	default void createStamp() {}
	
    default boolean save() {
    		createStamp();
        return $().insertTemplate(this) > 0;
    }

    default boolean saveDo() {
    		createStamp();	    		
        return $().insertTemplate(this, true) > 0;
    }
    
    default boolean update() {
    		return update(true);
    }

    default boolean update(boolean ignoreEmptyProperty) {
    		updateStamp();
    		if(ignoreEmptyProperty == false) {
    			return $().updateById(this) > 0;
    		} else {
    			return  $().updateTemplateById(this) > 0;
    		}
    }
    
    default boolean delete() {
        return $().deleteObject(this) > 0;
    }
    
    default int upinsert() {
    		return $().upsert(this);
    }  
}
