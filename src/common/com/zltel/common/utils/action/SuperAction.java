package com.zltel.common.utils.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 基础 Action 父类
 * 
 * @author Wangch
 * 
 */
public class SuperAction extends ActionSupport {
	/** 执行消息 */
	private String excMsg;

	/**
	 * 获取当前的httsession
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return this.getRequest().getSession();
	}

	/**
	 * 获取requst对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获取response对象
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public String getExcMsg() {
		return excMsg;
	}

	public void setExcMsg(String excMsg) {
		this.excMsg = excMsg;
	}
}
