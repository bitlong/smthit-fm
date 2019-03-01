/**
 * 
 */
package com.smthit.lang2.utils;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author Bean
 *
 */
public interface Property<T, R> extends Function<T, R>, Serializable {

}
