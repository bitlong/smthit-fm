/**
 *
 */
package org.beetl.sql.ext;

import java.util.Collection;

import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * @author john
 *
 */
public class ArrayLikeFunction implements Function {

	@Override
	public Boolean call(Object[] paras,Context ctx){
		Object temp = paras[0];
		if(temp instanceof Object[]){
			return true;
		}else if(temp instanceof Collection){
			return true;
		}else{
			return false;
		}
	}

}

