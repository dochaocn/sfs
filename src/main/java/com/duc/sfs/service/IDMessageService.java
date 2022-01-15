package com.duc.sfs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.duc.sfs.entity.DMessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Dc
 * @since 2021-08-19
 */
public interface IDMessageService extends IService<DMessage> {

    IPage<DMessage> queryPageParam(Integer id, String key, String date, Integer page, Integer size);

    boolean addData(String key, String message);

    boolean deleteData(Integer id);
}
