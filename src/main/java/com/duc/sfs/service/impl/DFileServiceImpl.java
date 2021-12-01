package com.duc.sfs.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.duc.sfs.entity.DFile;
import com.duc.sfs.enums.DeleteStatus;
import com.duc.sfs.mapper.DFileMapper;
import com.duc.sfs.service.IDFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.Arrays;

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
public class DFileServiceImpl extends ServiceImpl<DFileMapper, DFile> implements IDFileService {

    @Resource
    private DFileMapper fileMapper;

    @Value("${upload.filepath}")
    private String uploadFilepath; // 上传路径

    @Override
    public IPage<DFile> queryPageParam(Integer id, String filename, String date, Integer page, Integer size) {
        QueryWrapper<DFile> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc(Arrays.asList("d_update", "d_id"));
        if (id != null)
            wrapper.eq("d_id", id);
        if (StrUtil.isNotBlank(filename))
            wrapper.like("d_filename", filename);
        if (StrUtil.isNotBlank(date))
            wrapper.ge("d_update", date);
        wrapper.eq("d_delete", DeleteStatus._ON.getCode());
        IPage<DFile> iPage = new Page<>(page, size);
        wrapper.select("d_id", "d_filename", "d_size", "d_update");
        this.page(iPage, wrapper);
        return iPage;
    }

    @Override
    public void uploadFile(MultipartFile file) {
        byte[] bytes;
        try {
            try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                int len;
                byte[] buffer = new byte[1024];
                while ((len = bis.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                bytes = baos.toByteArray();
            }
        } catch (IOException e) {
            log.error("上传文件保存失败", e);
            return;
        }
        DFile fileObj = new DFile();
        fileObj.setDByte(bytes);
        fileObj.setDFilename(file.getOriginalFilename());
        fileObj.setDSize(this.fileSizeStr(file.getSize()));
        fileObj.setDDelete(DeleteStatus._ON.getCode());
        this.save(fileObj);
    }

    @Override
    public DFile queryFileById(Integer id) {
        return this.getById(id);
    }

    @Override
    public boolean deleteData(Integer id) {
        UpdateWrapper<DFile> wrapper = new UpdateWrapper<>();
        wrapper.eq("d_id", id);
        wrapper.set("d_delete", DeleteStatus._OFF.getCode());
        return this.update(wrapper);
    }

    private String fileSizeStr(long size) {
        String sizeStr;
        if (size > 1024L * 1024L * 1024L) { // GB
            sizeStr = size / (1024L * 1024L * 1024L) + "GB";
        } else if (size > 1024L * 1024L) { // MB
            sizeStr = size / (1024L * 1024L) + "MB";
        } else if (size > 1024L) { // KB
            sizeStr = size / (1024L) + "KB";
        } else {
            sizeStr = size + "B";
        }
        return sizeStr;
    }
}
