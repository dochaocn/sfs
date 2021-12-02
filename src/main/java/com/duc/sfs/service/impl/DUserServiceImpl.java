package com.duc.sfs.service.impl;

import com.duc.sfs.entity.DUser;
import com.duc.sfs.mapper.DUserMapper;
import com.duc.sfs.service.IDUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Dc
 * @since 2021-08-19
 */
@Slf4j
@Service
public class DUserServiceImpl extends ServiceImpl<DUserMapper, DUser> implements IDUserService {

}
