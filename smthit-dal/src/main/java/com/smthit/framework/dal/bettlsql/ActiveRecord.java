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

	
    default boolean save() {
        return $().insertTemplate(this) > 0;
    }

    default boolean saveDo() {
        return $().insertTemplate(this, true) > 0;
    }

    
    default boolean update() {
        return $().updateTemplateById(this) > 0;
    }

    default boolean delete() {
        return $().deleteObject(this) > 0;
    }
}
