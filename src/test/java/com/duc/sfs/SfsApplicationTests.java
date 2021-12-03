package com.duc.sfs;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;

import com.duc.sfs.entity.Daily;
import com.duc.sfs.service.DailyService;

import com.duc.sfs.service.SmsService;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.date.LocalDateTimeUtil;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class SfsApplicationTests {

    @Resource
    private SmsService smsService;
    @Test
    void sms() {
        smsService.statistics();
    }


    @Test
    void contextLoads() {
        final String now = Instant.now().toString();
        System.out.println(now);
        final String format = LocalDateTimeUtil.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        System.out.println(format);

    }

    @Resource
    private DailyService dailyService;
    
    @Test
    void daily() {
        dailyService.addDaily("2021-12-04");
        Daily daily = dailyService.getByDay("2021-12-04");
    }

    @Test
    void wordToHtml() {
//        String type = "FOF型";
//        String type = "FOF型改";
//        String type = "测试QDII重置01";
//        String type = "2指数型";
        String type = "QDII-004";
//        String type = "基金的收益与分配合同文件";
//        String type = "基金收入分配ETF";
//        String type = "合同文件_手动copy";
//        String type = "合同文件1";
        String path = "C:\\Users\\Dc\\Downloads\\model\\";
        String readPath = path + type + ".docx";
        String html = "";
        try (XWPFDocument doc = new XWPFDocument(new FileInputStream(readPath)); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XHTMLOptions options = XHTMLOptions.create().indent(4);
            options.setIgnoreStylesIfUnused(false);
            options.setFragment(true);
            XHTMLConverter.getInstance().convert(doc, out, options);
            StringBuilder sb = new StringBuilder("<html><head></head><body class=\"b1 b2\">");
            sb.append(new String(out.toByteArray(), StandardCharsets.UTF_8));
            sb.append("</body></html>");
            html = StringEscapeUtils.unescapeHtml3(sb.toString());
        } catch (Exception e) {
            log.error("错误", e);
        }

        String savePath = path + type + ".html";
        try (FileOutputStream fos = new FileOutputStream(savePath)) {
            fos.write(html.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("错误", e);
        }


    }

}
