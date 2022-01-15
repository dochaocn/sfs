package com.duc.sfs.controller;

import javax.annotation.Resource;

import com.duc.sfs.dto.JsonMessage;
import com.duc.sfs.entity.Daily;
import com.duc.sfs.service.DailyService;

import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

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
@RequestMapping("/daily")
public class DailyController {

    @Resource
    private DailyService dailyService;

    @PostMapping("/get")
    public JsonMessage getDaily(String day) {
        JsonMessage result = new JsonMessage();
        Daily daily = dailyService.getByDay(day);
        result.setData(daily);
        return result;
    }

    public boolean addDaily(String day) {
        try {
            dailyService.addDaily(day);
        } catch (Exception e) {
            log.error("新增天气错误", e);
            return false;
        }
        return true;
    }

}
