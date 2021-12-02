package com.duc.sfs.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Daily {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String fxDate; // 预报日期
    private String sunrise; // 日出时间
    private String sunset; // 日落时间
    private String moonrise; // 月升时间
    private String moonset; // 月落时间
    private String moonPhase; // 月相名称
    private String tempMax; // 预报当天最高温度
    private String tempMin; // 预报当天最低温度
    private String textDay; // 预报白天天气状况文字描述，包括阴晴雨雪等天气状态的描述
    private String textNight; // 预报晚间天气状况文字描述，包括阴晴雨雪等天气状态的描述
    private String wind360Day; // 预报白天风向360角度
    private String windDirDay; // 预报白天风向
    private String windScaleDay; // 预报白天风力等级
    private String windSpeedDay; // 预报白天风速，公里/小时
    private String wind360Night; // 预报夜间风向360角度
    private String windDirNight; // 预报夜间当天风向
    private String windScaleNight; // 预报夜间风力等级
    private String windSpeedNight; // 预报夜间风速，公里/小时
    private String humidity; // 相对湿度，百分比数值
    private String precip; // 预报当天总降水量，默认单位：毫米
    private String pressure; // 大气压强，默认单位：百帕
    private String vis; // 能见度，默认单位：公里
    private String cloud; // 云量，百分比数值
    private String uvIndex; // 紫外线强度指数
    private LocalDateTime dUpdate;
    private String dDelete;

}
