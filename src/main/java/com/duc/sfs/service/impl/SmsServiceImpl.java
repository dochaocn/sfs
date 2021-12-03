package com.duc.sfs.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duc.sfs.service.IDDictionaryService;
import com.duc.sfs.service.SmsService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatusStatisticsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendStatusStatisticsResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private IDDictionaryService dictionaryService;

    private final static Map<String, String> params = new HashMap<>();
    private final static String TENCENT_CLOUD = "tencent.cloud";
    private final static String SECRET_ID = "tencent.cloud.secret.id";
    private final static String SECRET_KEY = "tencent.cloud.secret.key";
    private final static String APP_ID = "tencent.cloud.app.id";
    private final static String SIGN_NAME = "tencent.cloud.sign.name";
    private final static String TEMPLATE_ID = "tencent.cloud.template.id";

    @Override
    public boolean sendSms(String[] phoneNumberSet, String[] templateParamSet) {

        try {
            SmsClient client = this.getSmsClient();

            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            req.setPhoneNumberSet(phoneNumberSet);
            req.setSmsSdkAppId(params.get(APP_ID));
            req.setSignName(params.get(SIGN_NAME));
            req.setTemplateId(params.get(TEMPLATE_ID));
            req.setTemplateParamSet(templateParamSet);

            SendSmsResponse resp = client.SendSms(req); // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            String result = SendSmsResponse.toJsonString(resp);// 输出json格式的字符串回包
            log.info("回执信息, result={}", result);
        } catch (TencentCloudSDKException e) {
            log.error("发送短信失败", e);
            return false;
        }
        return true;
    }

    @Override
    public void statistics() {
        try {
            SmsClient client = this.getSmsClient();

            SendStatusStatisticsRequest req = new SendStatusStatisticsRequest();
            req.setBeginTime("2021120101");
            req.setEndTime("2030123123");
            req.setSmsSdkAppId(params.get(APP_ID));
            req.setLimit(0L);
            req.setOffset(0L);

            SendStatusStatisticsResponse resp = client.SendStatusStatistics(req);
            String result = SendStatusStatisticsResponse.toJsonString(resp);
            JSONObject jsonObject = JSON.parseObject(result);
            SendStatusStatistics statistics = JSON.parseObject(jsonObject.getString("SendStatusStatistics"), SendStatusStatistics.class);
            log.info("回执信息, statistics={}", statistics);
        } catch (TencentCloudSDKException e) {
            log.error("查询短信统计信息失败", e);
        }
    }

    private SmsClient getSmsClient() {
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密  密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(params.get(SECRET_ID), params.get(SECRET_KEY));
        HttpProfile httpProfile = new HttpProfile();  // 实例化一个http选项，可选的，没有特殊需求可以跳过
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile(); // 实例化一个client选项，可选的，没有特殊需求可以跳过
        clientProfile.setHttpProfile(httpProfile);
        return new SmsClient(cred, "ap-nanjing", clientProfile);  // 实例化要请求产品的client对象,clientProfile是可选的
    }

    @Data
    private static class SendStatus {
        private String serialNo; // 发送流水号
        private String phoneNumber; // 手机号码，E.164标准
        private String fee; // 计费条数
        private String sessionContext; // 用户 session 内容
        private String code; // 短信请求错误码
        private String message; // 短信请求错误码描述
        private String isoCode; // 国家码或地区码
    }

    @Data
    private static class SendStatusStatistics {
        private Integer feeCount; // 短信计费条数统计
        private Integer requestCount; // 短信提交量统计
        private Integer requestSuccessCount; // 短信提交成功量统计
    }

    @PostConstruct
    public void getTencentCloud() {
        dictionaryService.queryDictionaryByUniqueKeyLike(TENCENT_CLOUD).forEach(dictionary -> params.put(dictionary.getDKey(), dictionary.getDValue()));
    }
}
