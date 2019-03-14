/**
 *
 */
package org.beetl.sql.core.db;

import java.util.Iterator;

import org.beetl.sql.core.annotation.NullTemplate;
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
			} else {
				StringTemplate stringTemplate = BeanKit.getAnnoation(classDesc.getTargetClass(), attr, StringTemplate.class);
				NullTemplate nullTemplate = BeanKit.getAnnoation(classDesc.getTargetClass(), attr, NullTemplate.class);

				boolean hasAnnotation = false;
				if (stringTemplate != null) {
					condition = condition + appendAnnotationLikeWhere(stringTemplate, col, attr);
					hasAnnotation = true;
				}
				if (nullTemplate != null) {
					condition = condition + appendAnnotationNullWhere(nullTemplate, col, attr);
					hasAnnotation = true;
				}
				// 没有任何标注，添加正常条件
				if (!hasAnnotation) {
					condition = condition + appendWhere(cls, table, col, attr);
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
				+ STATEMENT_END + connector + this.getKeyWordHandler().getCol(col) + " " + comp + " " + HOLDER_START + accept + HOLDER_END + lineSeparator + STATEMENT_START + "}" + STATEMENT_END;

		//如果没有模糊查询条件，添加精准查询
		sql += STATEMENT_START + "if(!isEmpty(" + prefix + fieldName + ") && isEmpty(" + prefix + accept + ")){"
				+ STATEMENT_END + connector + this.getKeyWordHandler().getCol(col) + "=" + HOLDER_START + prefix + fieldName
				+ HOLDER_END + lineSeparator + STATEMENT_START + "}" + STATEMENT_END;

		return sql;
	}

	protected String appendAnnotationNullWhere(NullTemplate t, String col, String fieldName) {
		String accept = t.accept();
		String nullValue = t.nullValue();

		String prefix = "";

		String connector = " and ";

		// 三元操作符判断是否 is null 。不知道为什么，nullValue作为一个字符串，需要用单引号括起来。
		String nullExp = HOLDER_START + "text(" + accept + "=='" + nullValue +  "'? 'is null' : 'is not null'" + ")" + HOLDER_END;

		String sql = STATEMENT_START + "if(!isEmpty(" + prefix + accept + ")){"
				+ STATEMENT_END + connector + this.getKeyWordHandler().getCol(col) + " " + nullExp + lineSeparator + STATEMENT_START + "}" + STATEMENT_END;

		return sql;
	}
}
