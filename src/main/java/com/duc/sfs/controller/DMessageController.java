package com.duc.sfs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.duc.sfs.dto.JsonMessage;
import com.duc.sfs.dto.JsonPage;
import com.duc.sfs.entity.DMessage;
import com.duc.sfs.enums.ReturnCode;
import com.duc.sfs.service.IDMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
@RequestMapping("/message")
public class DMessageController {

    @Resource
    private IDMessageService messageService;

    @GetMapping("/getById")
    public JsonMessage getMessage(Integer id) {
        JsonMessage result = new JsonMessage();
        DMessage message = messageService.getById(id);
        result.setData(message);
        return result;
    }

    @PostMapping("/query")
    public JsonMessage query(@RequestParam(required = false) Integer id, @RequestParam(required = false) String key, @RequestParam(required = false) String date,
                             @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        JsonPage<DMessage> jsonPage = new JsonPage<>();
        try {
            IPage<DMessage> iPage = messageService.queryPageParam(id, key, date, page, size);
            jsonPage.setTotal(iPage.getTotal());
            jsonPage.setRows(iPage.getRecords());
        } catch (Exception e) {
            jsonPage.setCode(ReturnCode._500.getCode());
            jsonPage.setMessage("查询列表出错");
            log.error("查询列表出错", e);
        }
        return jsonPage;
    }

    @PostMapping("/add")
    public JsonMessage add(@RequestParam String key, @RequestParam String message) {
        JsonMessage result = new JsonMessage();
        try {
            if (!messageService.addData(key, message))
                result.setCode(ReturnCode._500.getCode());
        } catch (Exception e) {
            result.setCode(ReturnCode._500.getCode());
            result.setMessage("增加记录出错");
            log.error("增加记录出错", e);
        }
        return result;
    }

    @PostMapping("/delete")
    public JsonMessage delete(@RequestParam Integer id) {
        JsonMessage result = new JsonMessage();
        try {
            if (!messageService.deleteData(id))
                result.setCode(ReturnCode._500.getCode());
        } catch (Exception e) {
            result.setCode(ReturnCode._500.getCode());
            result.setMessage("删除记录出错");
            log.error("删除记录出错", e);
        }
        return result;
    }
}
