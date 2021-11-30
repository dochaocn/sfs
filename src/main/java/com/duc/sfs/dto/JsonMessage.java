package com.bsb.rps.dto;

import java.io.Serializable;

/**
 * 业务操作统一返回结果
 * @author linyang
 *
 */
public class JsonMessage implements Serializable {

	/**
	 * 操作结果
	 * 成功：200
	 * 失败：500
	 */
	private String code = "200";

	/**
	 * 处理结果信息
	 */
	private String message="操作成功";

	private Object data;


	private static final long serialVersionUID = 2501645714671290602L;

	public JsonMessage(){
	}

	public JsonMessage(String code,String message) {
		this.code=code;
		this.message=message;
	}
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



}
