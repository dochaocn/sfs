package com.duc.sfs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Dc
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "d_id", type = IdType.AUTO)
    private Long dId;

    private String dKey;

    private String dMsg;

    private LocalDateTime dUpdate;

    private String dDelete;

}
