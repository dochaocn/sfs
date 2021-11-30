package com.duc.sfs.enums;

import lombok.Getter;

public enum ReturnCode {
    _200("200", "操作成功"),
    _500("500", "操作失败"),
    ;

    @Getter
    private final String code;
    @Getter
    private final String desc;

    ReturnCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ReturnCode judgeValue(String code) {
        ReturnCode[] values = values();
        for (ReturnCode value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

}
