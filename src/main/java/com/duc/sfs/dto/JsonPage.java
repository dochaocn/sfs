package com.duc.sfs.dto;

import com.duc.sfs.enums.ReturnCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class JsonPage<T> extends JsonMessage {
    private long total;
    private Collection<T> rows;

    public JsonPage() {
        this.setCode(ReturnCode._200.getCode());
        this.setMessage("操作成功");
    }

    private static final long serialVersionUID = -3952581818930815279L;
}
