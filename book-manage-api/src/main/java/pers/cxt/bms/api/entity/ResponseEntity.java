package pers.cxt.bms.api.entity;

import pers.cxt.bms.api.enums.ErrorCodes;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
public class ResponseEntity {

    private boolean suc;

    private Object value;

    private String msg;

    private String errCode;

    public ResponseEntity() {

    }

    public ResponseEntity(boolean suc, String errCode, Object value, String msg) {
        this.suc = suc;
        this.value = value;
        this.msg = msg;
        this.errCode = errCode;
    }

    public ResponseEntity(boolean suc, Object value, String msg) {
        this.suc = suc;
        this.value = value;
        this.msg = msg;
    }

    public ResponseEntity(String errCode, String msg) {
        this.suc = false;
        this.errCode = errCode;
        this.msg = msg;
    }

    public ResponseEntity(ErrorCodes errorCode) {
        this.suc = false;
        this.msg = errorCode.getErrMsg();
        this.errCode = errorCode.getCode();
    }

    public boolean isSuc() {
        return suc;
    }

    public void setSuc(boolean suc) {
        this.suc = suc;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
