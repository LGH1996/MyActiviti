package com.lgh.activiti;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.Task;
import org.activiti.engine.*;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Consumer;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class MyTest {
    @Resource
    private ProcessEngine processEngine;


    @Test
    public void test1() {
        Deployment deployment = processEngine.getRepositoryService().createDeployment()
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
    }}
    @Test
    public void test4() {
        processEngine.getHistoryService().createHistoricTaskInstanceQuery().taskAssignee("lgh").finished().list().forEach(e->{
            log.info(e.getCategory() + e.getId() + e.getName());
        });
    }
}
