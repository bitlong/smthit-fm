/**
 * 
 */
package com.smthit.framework.dal;

import org.beetl.sql.core.TailBean;

import com.smthit.framework.dal.bettlsql.convert.BeetlTailReformer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Bean
 *
 */
public class BeetlTailReformerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TestPO po = new TestPO();
		
		po.setStr("测试1");
		po.setVal(1);
		po.setVal2(2L);
		
		po.set("bell_department", "技术一部");
		
		TestVO vo = new BeetlTailReformer<TestPO, TestVO>(TestVO.class).toVO(po);
		
		System.out.println(vo);
	}

	@Data
	@EqualsAndHashCode(callSuper = true)
	@ToString
	public static class TestPO extends TailBean {
		private static final long serialVersionUID = -6020275921087067503L;

		private String str;
		private Integer val;
		private Long val2;
		
	}
	
	@Data
	@EqualsAndHashCode(callSuper = true)
	@ToString
	public static class TestVO extends TailBean {
		private static final long serialVersionUID = -6020275921087067503L;

		private String str;
		private Integer val;
		private Long val2;
	
		private String bellDepartment;
	}
	
}
