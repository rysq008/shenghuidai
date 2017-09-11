package com.delevin.shenghuidai.bean;

import java.io.Serializable;

public class BeanBase<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 863351815695556473L;

	public String code;
	public String desc;
	public T content;
}
