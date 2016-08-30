package com.zltel.common.utils.pub;
/**
 * @author chenml Aug 19, 2011 create
 * 
 */
public final class UUID {
	public static String getUUID(){
		return java.util.UUID.randomUUID().toString();
	}
}
