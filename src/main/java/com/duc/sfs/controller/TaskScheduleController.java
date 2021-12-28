package com.duc.sfs.controller;

import cn.hutool.core.util.StrUtil;
import com.duc.sfs.dto.JsonMessage;
import com.duc.sfs.entity.Daily;
import com.duc.sfs.enums.ReturnCode;
import com.duc.sfs.service.DailyService;
import com.duc.sfs.service.SmsService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
@EnableScheduling
public class TaskScheduleController {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Resource
    private DailyController dailyController;
    @Resource
    private SmsService smsService;
    @Resource
    private DailyService dailyService;

    @Scheduled(cron = "0 0 12-17 * * ?")
    public void cron_1() {
        String day = dtf.format(LocalDate.now().plusDays(1L));
        this.execute("daily", day);
    }

    @Scheduled(cron = "0 0 21 * * ?")
    public void cron_2() {
        String day = dtf.format(LocalDate.now().plusDays(1L));
        this.sendDailySms(day);
    }

    @PostMapping("/execute")
    public JsonMessage execute(String controller, String param) {
        log.info("调度任务请求, controller={}, param={}", controller, param);
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

    @PostMapping("sendDailySms")
    public JsonMessage sendDailySms(String param) {
        JsonMessage result = new JsonMessage();
        Daily daily = dailyService.getByDay(param);
        if(daily == null || daily.getFxDate() == null) {
            result.setMessage("daily is null");
            result.setCode(ReturnCode._500.getCode());
            return result;
        }
        String[] templateParam = new String[2];
        templateParam[0] = daily.getFxDate();
        templateParam[1] = daily.getTextDay() + "，" + daily.getTempMax() + "-" + daily.getTempMin();
        smsService.sendSms(new String[]{"+8618522812996"}, templateParam);
        return result;
    }
}
