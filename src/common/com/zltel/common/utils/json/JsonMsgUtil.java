package com.zltel.common.utils.json;

import com.zltel.common.utils.json.bean.JsonMsgBean;

/**
 * JSON 信息创建工具类
 * 
 * @author Wangch
 * 
 */
public class JsonMsgUtil {

	public static final JsonMsgBean createJsonMsg(boolean success, String msg) {
		JsonMsgBean jmb = new JsonMsgBean();
		jmb.setSuccess(success);
		jmb.setMsg(msg);
		return jmb;
	}

	public static void fail(JsonMsgBean jsonMsgBean, String message) {
		jsonMsgBean.setSuccess(false);
		jsonMsgBean.setMsg(message);
	}

}
