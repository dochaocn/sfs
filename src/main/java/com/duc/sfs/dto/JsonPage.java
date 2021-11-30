package com.bsb.rps.dto;

import java.io.Serializable;
import java.util.Collection;

public class JsonPage<T> extends JsonMessage implements Serializable {
	/**
	 * 总数
	 */
	private int total;

	/**
	 * 数据
	 */
	private Collection<T> rows;

	public JsonPage(){
		this.setCode("200");
		this.setMessage("操作成功");
	}

	private static final long serialVersionUID = -3952581818930815279L;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Collection<T> getRows() {
		return rows;
	}

	public void setRows(Collection<T> rows) {
		this.rows = rows;
	}


}
