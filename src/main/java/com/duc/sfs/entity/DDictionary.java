package com.duc.sfs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Dc
 * @since 2021-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "d_id", type = IdType.AUTO)
    private Long dId;

    private String dKey;

    private String dValue;

    private LocalDateTime dUpdate;

    private String dDelete;

}
