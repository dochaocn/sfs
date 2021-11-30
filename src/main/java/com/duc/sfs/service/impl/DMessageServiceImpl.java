package com.duc.sfs.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.duc.sfs.entity.DMessage;
import com.duc.sfs.enums.DeleteStatus;
import com.duc.sfs.mapper.DMessageMapper;
import com.duc.sfs.service.IDMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Dc
 * @since 2021-08-19
 */
@Service
public class DMessageServiceImpl extends ServiceImpl<DMessageMapper, DMessage> implements IDMessageService {

    @Resource
    private DMessageMapper messageMapper;

    @Override
    public IPage<DMessage> queryPageParam(Integer id, String key, String date, Integer page, Integer size) {
        QueryWrapper<DMessage> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc(Arrays.asList("d_update", "d_id"));
        if (id != null)
            wrapper.eq("d_id", id);
        if (StrUtil.isNotEmpty(key))
            wrapper.likeRight("d_key", key);
        if (StrUtil.isNotEmpty(date))
            wrapper.ge("d_update", date);
        wrapper.eq("d_delete", DeleteStatus._ON.getCode());
        IPage<DMessage> iPage = new Page<>(page, size);
        this.page(iPage, wrapper);
        return iPage;
    }

    @Override
    public boolean addData(String key, String message) {
        DMessage msg = new DMessage();
        msg.setDKey(key);
        msg.setDMsg(message);
        msg.setDDelete(DeleteStatus._ON.getCode());
        return this.save(msg);
    }

    @Override
    public boolean deleteData(Integer id) {
        UpdateWrapper<DMessage> wrapper = new UpdateWrapper<>();
        wrapper.eq("d_id", id);
        wrapper.set("d_delete", DeleteStatus._OFF.getCode());
        return this.update(wrapper);
    }
}
