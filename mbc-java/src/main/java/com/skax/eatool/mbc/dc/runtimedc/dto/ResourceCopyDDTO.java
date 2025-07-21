package com.skax.eatool.mbc.dc.runtimedc.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;

public class ResourceCopyDDTO extends NewAbstractDTO {

	private static final long serialVersionUID = 1L;

	private String uapplDstcd;
	private String cmpoIdnfr;
	private String fileIdnfr;
	private String kywrCtnt;

	private String originfilePathName;
	private String dsttfilePathName;

	private String originfileSize;
	private String dsttfileSize;

	private StringBuffer logBuffer;

	public StringBuffer getLogBuffer() {
		return logBuffer;
	}

	public void setLogBuffer(StringBuffer logBuffer) {
		this.logBuffer = logBuffer;
	}

	public String getCmpoIdnfr() {
		return cmpoIdnfr;
	}

	public void setCmpoIdnfr(String cmpoIdnfr) {
		this.cmpoIdnfr = cmpoIdnfr;
	}

	public String getDsttfilePathName() {
		return dsttfilePathName;
	}

	public void setDsttfilePathName(String dsttfilePathName) {
		this.dsttfilePathName = dsttfilePathName;
	}

	public String getDsttfileSize() {
		return dsttfileSize;
	}

	public void setDsttfileSize(String dsttfileSize) {
		this.dsttfileSize = dsttfileSize;
	}

	public String getFileIdnfr() {
		return fileIdnfr;
	}

	public void setFileIdnfr(String fileIdnfr) {
		this.fileIdnfr = fileIdnfr;
	}

	public String getKywrCtnt() {
		return kywrCtnt;
	}

	public void setKywrCtnt(String kywrCtnt) {
		this.kywrCtnt = kywrCtnt;
	}

	public String getOriginfilePathName() {
		return originfilePathName;
	}

	public void setOriginfilePathName(String originfilePathName) {
		this.originfilePathName = originfilePathName;
	}

	public String getOriginfileSize() {
		return originfileSize;
	}

	public void setOriginfileSize(String originfileSize) {
		this.originfileSize = originfileSize;
	}

	public String getUapplDstcd() {
		return uapplDstcd;
	}

	public void setUapplDstcd(String uapplDstcd) {
		this.uapplDstcd = uapplDstcd;
	}
}
