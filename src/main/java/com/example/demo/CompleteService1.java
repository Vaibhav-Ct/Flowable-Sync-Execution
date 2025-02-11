package com.example.demo;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class CompleteService1 implements JavaDelegate {

    public void execute(DelegateExecution execution) {
        System.out.println("Calling the Service 1 Task..." + execution.getProcessInstanceId());
    }

}
