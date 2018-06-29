package com.cash.spring.aop.plugin;

/**
 * author cash
 * create 2017-10-13-11:19
 **/

public class TstObjSup {

    private String idCardNo;

    private boolean  foreign;

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public boolean isForeign() {
        return foreign;
    }

    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }

    public TstObjSup(String idCardNo, boolean foreign) {
        this.idCardNo = idCardNo;
        this.foreign = foreign;
    }

    @Override
    public String toString() {
        return "TstObjSup{" +
                "idCardNo='" + idCardNo + '\'' +
                ", foreign=" + foreign +
                '}';
    }
}
