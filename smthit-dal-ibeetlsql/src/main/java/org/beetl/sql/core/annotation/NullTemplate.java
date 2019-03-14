/**
 *
 */
package org.beetl.sql.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author john
 * <pre>
 * 生成 colName is [not] null 的语句。
 * 例如 PO 对象为
 * <code>
 *
 * class TestPO {
 *   private Long id;
 *   {@code @NullTemplate}(accept = "hasName")
 *   private String name;
 * }
 * </code>
 * 那么查询条件为 {"hasName": "0"} 时生成 where name is null 的语句；查询条件为 {"hasName": "any other value"} 时生成 where name is not null 类似语句
 * 没有 hasName 条件不生成对应语句
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.FIELD})
public @interface NullTemplate {

	String accept() default ""; //null条件字段名

	String nullValue() default "0"; //is null条件。除去该值，都算作 is not null

}
