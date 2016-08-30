package com.zltel.common.utils.json.bean;

public class JsonMsgBean {
	/** 类型 **/
	public String type;
	/** 消息 **/
	public String msg;
	/** 是否成功 **/
	public boolean success;

	/** 下载url **/
	public String url;

	public Object data;

	public long costtime;

	public final String getType() {
		return type;
	}

	public final String getMsg() {
		return msg;
	}

	public final boolean isSuccess() {
		return success;
	}

	public final Object getData() {
		return data;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}

	public final void setSuccess(boolean success) {
		this.success = success;
	}

	public final void setData(Object data) {
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the costtime
	 */
	public long getCosttime() {
		return costtime;
	}

	/**
	 * @param costtime
	 *            the costtime to set
	 */
	public void setCosttime(long costtime) {
		this.costtime = costtime;
	}

}
