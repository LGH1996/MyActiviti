package com.lgh.activiti;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class MyTest {
    @Resource
    private ProcessEngine processEngine;


    @Test
    public void test1() {
        Deployment deployment = processEngine.getRepositoryService().createDeployment()
                .name("test")
                .addClasspathResource("bpmn/test.bpmn20.xml")
                .deploy();
        log.info(deployment.getId() + deployment.getName() + deployment.getKey() + deployment.getCategory() + deployment.getTenantId());
    }
    @Test
    public void test2() {
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("test");
        log.info(processInstance.getId());
    }
    @Test
    public void test3() {{
        processEngine.getTaskService().createTaskQuery().taskAssignee("lgh").list().forEach(e->{
            log.info(e.getId());
            processEngine.getTaskService().complete(e.getId());
        });
    }
    }

    @Test
    public void test4() {
        processEngine.getHistoryService().createHistoricTaskInstanceQuery().taskAssignee("lgh").finished().list().forEach(e -> {
            log.info(e.getCategory() + e.getId() + e.getName());
        });
    }

    @Resource
    MyBeanMapper myBeanMapper;

    @Test
    public void test5() {
        MyBean myBean = myBeanMapper.selectByName("lingh");
        log.info("lingh:{}", myBean);
        log.info(new BCryptPasswordEncoder().encode("123456"));
    }

    @Test
    public void test6() {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("aa");
        linkedHashSet.add("bb");
        linkedHashSet.add("aa");
        log.info(linkedHashSet + "");
        Long l = 2L;
        Integer i = null;
        if (l >= i) {
            log.info("aaaaaaaaaaaa");
        }
    }

    @Test
    public void test7() {
        Map<String, String> query = new TreeMap<>();
        query.put("timestamp", System.currentTimeMillis() + "");
        query.put("companyid", "SRM");


        Map<String, String> body = new TreeMap<>();
        body.put("id", "sss");

        final StringBuilder strBuildQuery = new StringBuilder();
        query.forEach((k, v) -> {
            strBuildQuery.append("&").append(k).append("=").append(v);
        });
        String strQuery = strBuildQuery.substring(1);

        StringBuilder strBuildBody = new StringBuilder();
        body.forEach((k, v)-> {
            strBuildBody.append("&").append(k).append("=").append(v);
        });
        String strBody = strBuildBody.substring(1);

        String a = DigestUtils.sha1Hex((strQuery + "&" + strBody + "&publicKey=F7669C6A-324C-40E2-B4B0-52B74C10CF29").getBytes());
        String sign = Base64.encodeBase64String(a.getBytes());

        String url = "http://10.10.11.236:8080/api/v2/CtsApplyToken/Passed?"  + "hash=" + sign + "&" + strQuery;

       /* RestTemplate restTemplate = new RestTemplate();
        String url = "http://10.10.11.236:8080/api/v2/CtsApplyToken/Rejected?"+ stringBuilder + "hash=" + sign;
        String re = restTemplate.postForObject(url, null, String.class);
        log.info(re);*/

        /*OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = FormBody.create(JSON.toJSONString(body) , MediaType.parse("application/x-www-form-urlencoded"));
                new FormBody.Builder().add("id", "sss").build();
                RequestBody.create(strBody , MediaType.parse("application/x-www-form-urlencoded"));
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try (Response response = client.newCall(request).execute()) {
            log.info(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/


        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("id", "sss");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        ResponseEntity<Boolean> response = client.exchange(url, HttpMethod.POST, requestEntity, Boolean.class);
        log.info(response.getBody()+"");
    }
}
