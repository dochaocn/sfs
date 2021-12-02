package com.duc.sfs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duc.sfs.entity.Daily;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Dc
 * @since 2021-12-01
 */
public interface DailyService extends IService<Daily> {

    Daily getByDay(String day);

    void addDaily(String day);

}
