package com.zltel.common.utils.action;

import com.zltel.common.utils.json.JsonMsgUtil;
import com.zltel.common.utils.json.bean.JsonMsgBean;

/**
 * struts2 返回Ajax数据 支持类
 * 
 * @author Wangch
 *
 */
public class AjaxResultSupport extends SuperAction {
	private JsonMsgBean jsonMsgBean;
	private long _time;

	public AjaxResultSupport() {
		_time = System.currentTimeMillis();
		jsonMsgBean = JsonMsgUtil.createJsonMsg(true, "请求成功");
	}

	/**
	 * 设置返回结果
	 * 
	 * @param data
	 *            要返回的数据
	 */
	public void setData(Object data) {
		jsonMsgBean.setData(data);
		jsonMsgBean.setCosttime(System.currentTimeMillis() - _time);
	}

	/**
	 * 设置 请求失败
	 * 
	 * @param msg
	 *            失败原因信息
	 */
	public void fail(String msg) {
		JsonMsgUtil.fail(jsonMsgBean, msg);
		jsonMsgBean.setCosttime(System.currentTimeMillis() - _time);
	}

	/**
	 * @return the jsonMsgBean
	 */
	public JsonMsgBean getJsonMsgBean() {
		return jsonMsgBean;
	}

	/**
	 * @param jsonMsgBean
	 *            the jsonMsgBean to set
	 */
	public void setJsonMsgBean(JsonMsgBean jsonMsgBean) {
		this.jsonMsgBean = jsonMsgBean;
	}

}
