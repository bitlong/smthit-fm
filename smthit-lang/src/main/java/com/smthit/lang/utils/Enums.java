package com.smthit.lang.utils;

public interface Enums<V, D> {
	public V getValue();
	public String getDesc();
	public Enums<V,D> getEnums(String value);
}
