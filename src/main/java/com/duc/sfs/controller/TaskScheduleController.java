package com.duc.sfs.controller;

import cn.hutool.core.util.StrUtil;
import com.duc.sfs.dto.JsonMessage;
import com.duc.sfs.enums.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Dc
 * @since 2021-12-01
 */
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/task")
public class TaskScheduleController {

    @Resource
    private DailyController dailyController;

    @GetMapping("/execute")
    public JsonMessage execute(String controller, String param) {
        JsonMessage result = new JsonMessage();
        if (StrUtil.isBlank(controller) || StrUtil.isBlank(param)) {
            result.setMessage("controller or param is blank");
            result.setCode(ReturnCode._500.getCode());
        }
        if ("daily".equals(controller)) {
            boolean flag = dailyController.addDaily(param);
            if (!flag) {
                result.setMessage("add daily error");
                result.setCode(ReturnCode._500.getCode());
            }
        }

        return result;
    }

}
