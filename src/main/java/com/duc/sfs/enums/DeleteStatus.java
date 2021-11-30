package com.duc.sfs.enums;

import lombok.Getter;

public enum DeleteStatus {
    _ON("0", "未删除"),
    _OFF("1", "已删除"),
    ;

    @Getter
    private final String code;
    @Getter
    private final String desc;

    DeleteStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DeleteStatus judgeValue(String code) {
        DeleteStatus[] values = values();
        for (DeleteStatus value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
