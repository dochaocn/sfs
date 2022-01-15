package com.duc.sfs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duc.sfs.entity.DDictionary;
import com.duc.sfs.mapper.DDictionaryMapper;
import com.duc.sfs.service.IDDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Dc
 * @since 2021-12-01
 */
@Slf4j
@Service
public class DDictionaryServiceImpl extends ServiceImpl<DDictionaryMapper, DDictionary> implements IDDictionaryService {

    @Resource
    private DDictionaryMapper dictionaryMapper;

    @Override
    public DDictionary queryDictionaryByUniqueKey(String key) {
        log.info("方法名={}, 参数={}", "queryDictionaryByUniqueKey", key);
        QueryWrapper<DDictionary> wrapper = new QueryWrapper<>();
        wrapper.eq("d_key", key);
        return this.getOne(wrapper);
    }

    @Override
    public String queryValueByUniqueKey(String key) {
        DDictionary dictionary = this.queryDictionaryByUniqueKey(key);
        return dictionary == null ? null : dictionary.getDValue();
    }

    @Override
    public List<DDictionary> queryDictionaryByUniqueKeyLike(String likeKey) {
        QueryWrapper<DDictionary> wrapper = new QueryWrapper<>();
        wrapper.likeRight("d_key", likeKey);
        return this.list(wrapper);
    }
}
