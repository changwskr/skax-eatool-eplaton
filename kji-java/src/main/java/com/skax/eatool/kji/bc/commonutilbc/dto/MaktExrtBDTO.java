package com.skax.eatool.kji.bc.commonutilbc.dto;

import java.util.List;

/**
 * Stub class for MaktExrtBDTO
 */
public class MaktExrtBDTO {

    private String inGroupCoCd;
    private String inDstcd;
    private String inBaseYmd;
    private String inCncycd;
    private int inExrtAplyNth;
    private String rtnStat;
    private List<MaktExrtListBDTO> outMaktExrtList;

    public MaktExrtBDTO() {
        // Stub constructor
    }

    public String getInGroupCoCd() {
        return inGroupCoCd;
    }

    public void setInGroupCoCd(String inGroupCoCd) {
        this.inGroupCoCd = inGroupCoCd;
    }

    public String getInDstcd() {
        return inDstcd;
    }

    public void setInDstcd(String inDstcd) {
        this.inDstcd = inDstcd;
    }

    public String getInBaseYmd() {
        return inBaseYmd;
    }

    public void setInBaseYmd(String inBaseYmd) {
        this.inBaseYmd = inBaseYmd;
    }

    public String getInCncycd() {
        return inCncycd;
    }

    public void setInCncycd(String inCncycd) {
        this.inCncycd = inCncycd;
    }

    public int getInExrtAplyNth() {
        return inExrtAplyNth;
    }

    public void setInExrtAplyNth(int inExrtAplyNth) {
        this.inExrtAplyNth = inExrtAplyNth;
    }

    public String getRtnStat() {
        return rtnStat;
    }

    public void setRtnStat(String rtnStat) {
        this.rtnStat = rtnStat;
    }

    public List<MaktExrtListBDTO> getOutMaktExrtList() {
        return outMaktExrtList;
    }

    public void setOutMaktExrtList(List<MaktExrtListBDTO> outMaktExrtList) {
        this.outMaktExrtList = outMaktExrtList;
    }
}