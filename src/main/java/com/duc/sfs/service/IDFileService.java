package com.duc.sfs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.duc.sfs.entity.DFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Dc
 * @since 2021-08-19
 */
public interface IDFileService extends IService<DFile> {

    IPage<DFile> queryPageParam(Integer id, String filename, String date, Integer page, Integer size);

    void uploadFile(MultipartFile file);

    DFile queryFileById(Integer id);

    boolean deleteData(Integer id);
}
