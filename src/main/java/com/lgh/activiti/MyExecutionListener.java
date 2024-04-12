package com.lgh.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * @author Lenovo
 */
@Slf4j
public class MyExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        log.info(execution.toString());
    }
}
