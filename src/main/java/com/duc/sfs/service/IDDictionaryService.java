package com.duc.sfs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duc.sfs.entity.DDictionary;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Dc
 * @since 2021-12-01
 */
public interface IDDictionaryService extends IService<DDictionary> {

    DDictionary queryDictionaryByUniqueKey(String key);

    String queryValueByUniqueKey(String key);
}
