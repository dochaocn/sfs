package com.duc.sfs.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsonMessage implements Serializable {
    private String code = "200";
    private String message = "操作成功";
    private Object data;

    private static final long serialVersionUID = 2501645714671290602L;
}
