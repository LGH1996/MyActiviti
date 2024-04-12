package com.lgh.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

/**
 * @author Lenovo
 */
@Slf4j
public class MyEeventListener implements ActivitiEventListener {
    @Override
    public void onEvent(ActivitiEvent event) {
        log.info(event.toString());
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
