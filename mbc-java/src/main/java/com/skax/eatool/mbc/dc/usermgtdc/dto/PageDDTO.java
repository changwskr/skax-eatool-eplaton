package com.skax.eatool.mbc.dc.usermgtdc.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;

public class PageDDTO extends NewAbstractDTO {

	private static final long serialVersionUID = 1L;

	private int dmndPageNo;
	private int pageLineCnt;
	private Integer pageSize;
	private Integer offset;

	public int getDmndPageNo() {
		return dmndPageNo;
	}

	public void setDmndPageNo(String dmndPageNo) {
		this.dmndPageNo = Integer.parseInt(dmndPageNo);
	}

	public int getPageLineCnt() {
		return pageLineCnt;
	}

	public void setPageLineCnt(String pageLineCnt) {
		this.pageLineCnt = Integer.parseInt(pageLineCnt);
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}
}
