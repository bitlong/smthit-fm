/**
 * 
 */
package org.beetl.sql.core.db;

import java.util.Iterator;

import org.beetl.sql.core.annotation.StringTemplate;
import org.beetl.sql.core.annotatoin.DateTemplate;
import org.beetl.sql.core.kit.BeanKit;

/**
 * @author Bean
 *
 */
public class MySqlExtStyle extends MySqlStyle {

	/**
	 * 
	 */
	public MySqlExtStyle() {
	}

	@Override
    protected String getSelectTemplate(Class<?> cls) {
        String condition = " where 1=1 " + lineSeparator;
        String tableName = nameConversion.getTableName(cls);
        TableDesc table = this.metadataManager.getTable(tableName);
        ClassDesc classDesc = table.getClassDesc(cls, nameConversion);
        Iterator<String> cols = classDesc.getInCols().iterator();
        Iterator<String> attrs = classDesc.getAttrs().iterator();

        while (cols.hasNext() && attrs.hasNext()) {
            String col = cols.next();
            String attr = attrs.next();
            if (classDesc.isDateType(attr)) {
            		
                try {
                    DateTemplate dateTemplate = BeanKit.getAnnoation(classDesc.getTargetClass(), attr, DateTemplate.class);
                    if (dateTemplate == null){
                    		continue;
                    }
                    String sql = this.genDateAnnotatonSql(dateTemplate, cls, col);
                    condition = condition + sql;
                    continue;
                } catch (Exception e) {
                    //不可能发生
                    throw new RuntimeException("获取metod出错" + e.getMessage());
                }
            } 
            else {
            		StringTemplate stringTemplate = BeanKit.getAnnoation(classDesc.getTargetClass(), attr, StringTemplate.class);
            		
            		if(stringTemplate == null) {
            			condition = condition + appendWhere(cls, table, col, attr);
            		} else {
            			condition = condition + appendAnnotationLikeWhere(stringTemplate, col, attr);
            		}
            }
        }
        return condition;
    }
	
    protected String appendAnnotationLikeWhere(StringTemplate t, String col, String fieldName) {
        String accept = t.accept();
        String comp = t.compare();

        String prefix = "";

        String connector = " and ";
        String sql = STATEMENT_START + "if(!isEmpty(" + prefix + accept + ")){"
                + STATEMENT_END + connector + this.getKeyWordHandler().getCol(col) + " " + comp + " " + this.HOLDER_START + accept + HOLDER_END + lineSeparator + STATEMENT_START + "}" + STATEMENT_END;

        //如果没有模糊查询条件，添加精准查询
        sql += STATEMENT_START + "if(!isEmpty(" + prefix + fieldName + ") && isEmpty(" + prefix + accept + ")){"
                + STATEMENT_END + connector + this.getKeyWordHandler().getCol(col) + "=" + HOLDER_START + prefix + fieldName
                + HOLDER_END + lineSeparator + STATEMENT_START + "}" + STATEMENT_END;
        
        return sql;
    }
}
