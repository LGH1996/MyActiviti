package com.lgh.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.api.task.runtime.events.listener.TaskEventListener;

/**
 * @author Lenovo
 */
@Slf4j
public class MyTaskerListener implements TaskEventListener {
    @Override
    public void onEvent(RuntimeEvent runtimeEvent) {
        log.info(runtimeEvent.toString());
    }
}
