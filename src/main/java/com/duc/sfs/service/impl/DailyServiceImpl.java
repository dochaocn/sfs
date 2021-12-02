package com.duc.sfs.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duc.sfs.entity.Daily;
import com.duc.sfs.mapper.DailyMapper;
import com.duc.sfs.service.DailyService;

import com.duc.sfs.service.IDDictionaryService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Resource
    private DailyMapper dailyMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private IDDictionaryService dictionaryService;

    private final static Map<String, String> params = new HashMap<>();
    private final static String WEATHER_LOCATION = "weather.location";
    private final static String WEATHER_KEY = "weather.key";

    @Override
    public Daily getByDay(String day) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("fx_date", day);
        wrapper.orderByDesc("d_update");
        List<Daily> list = this.list(wrapper);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void addDaily(String day) {
        String jsonStr = "";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip");
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        URI uri = URI.create("https://devapi.qweather.com/v7/weather/3d?" + "location=" + params.get(WEATHER_LOCATION) + "&" + "key=" + params.get(WEATHER_KEY));
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, byte[].class);
        String contentEncoding = responseEntity.getHeaders().getFirst(HttpHeaders.CONTENT_ENCODING);
        byte[] body = responseEntity.getBody();
        if ("gzip".equals(contentEncoding)) {
            if (body != null) {
                try (ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(body);
                     ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
                     GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayIS)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = gzipInputStream.read(buffer, 0, buffer.length)) != -1) {
                        byteArrayOS.write(buffer, 0, len);
                    }
                    jsonStr = new String(byteArrayOS.toByteArray(), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    log.error("解压响应gzip错误", e);
                }
            }
        }

        if (StrUtil.isNotBlank(jsonStr)) {
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            String jsonArray = jsonObject.getString("daily");
            log.info("天气json字符串, jsonArray={}", jsonArray);
            List<Daily> dailyList = JSON.parseArray(jsonArray, Daily.class);
            dailyList.stream().filter(daily -> day.equals(daily.getFxDate())).findFirst().ifPresent(this::save); // 只把参数day这一天的天气情况保存数据库
        }
    }

    @PostConstruct
    public void getURIParam() {
        String location = dictionaryService.queryValueByUniqueKey(WEATHER_LOCATION);
        String key = dictionaryService.queryValueByUniqueKey(WEATHER_KEY);
        params.put(WEATHER_LOCATION, location);
        params.put(WEATHER_KEY, key);
    }
}
