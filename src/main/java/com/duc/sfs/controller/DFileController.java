package com.duc.sfs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.duc.sfs.dto.JsonMessage;
import com.duc.sfs.dto.JsonPage;
import com.duc.sfs.entity.DFile;
import com.duc.sfs.enums.ReturnCode;
import com.duc.sfs.service.IDFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Dc
 * @since 2021-08-19
 */
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/file")
public class DFileController {

    @Resource
    private IDFileService fileService;

    @GetMapping("/getById")
    public JsonMessage getMessage(Integer id) {
        JsonMessage result = new JsonMessage();
        DFile message = fileService.getById(id);
        result.setData(message);
        return result;
    }

    /**
     * 分页查询文件
     *
     * @param id       文件id
     * @param filename 文件名称
     * @param date     创建时间
     * @param page     页码
     * @param size     每页大小
     * @return JsonMessage 封装数据
     */
    @PostMapping("/query")
    public JsonMessage query(@RequestParam(required = false) Integer id, @RequestParam(required = false) String filename, @RequestParam(required = false) String date,
                             @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        JsonPage<DFile> jsonPage = new JsonPage<>();
        try {
            IPage<DFile> iPage = fileService.queryPageParam(id, filename, date, page, size);
            jsonPage.setTotal(iPage.getTotal());
            jsonPage.setRows(iPage.getRecords());
        } catch (Exception e) {
            jsonPage.setCode(ReturnCode._500.getCode());
            jsonPage.setMessage("查询列表出错");
            log.error("查询列表出错", e);
        }
        return jsonPage;
    }

    /**
     * 上传单个文件
     *
     * @param file 文件
     * @return JsonMessage 封装数据
     */
    @RequestMapping("/upload")
    public JsonMessage upload(MultipartFile file) {
        JsonMessage result = new JsonMessage();
        try {
            fileService.uploadFile(file);
        } catch (Exception e) {
            result.setCode(ReturnCode._500.getCode());
            result.setMessage("上传文件出错");
            log.error("上传文件出错", e);
        }
        return result;
    }

    /**
     * 下载单个文件
     *
     * @param id       文件id
     * @param response 响应
     */
    @GetMapping("/download")
    public void download(Integer id, HttpServletResponse response) {
        try (ServletOutputStream sos = response.getOutputStream()) {
            DFile file = fileService.queryFileById(id);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(file.getDFilename().getBytes(), "ISO8859-1"));
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST");
            response.setHeader("Access-Control-Allow-Headers", "Access-Control");
            response.setHeader("Allow", "GET");
            response.setContentType("application/octet-stream; charset=utf-8");
            sos.write(file.getDByte());
        } catch (Exception e) {
            log.error("下载文件出错", e);
        }
    }

    @PostMapping("/delete")
    public JsonMessage delete(@RequestParam Integer id) {
        JsonMessage result = new JsonMessage();
        try {
            if (!fileService.deleteData(id))
                result.setCode(ReturnCode._500.getCode());
        } catch (Exception e) {
            result.setCode(ReturnCode._500.getCode());
            result.setMessage("删除记录出错");
            log.error("删除记录出错", e);
        }
        return result;
    }

}
