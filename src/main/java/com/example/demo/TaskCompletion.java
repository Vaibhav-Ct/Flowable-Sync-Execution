package com.example.demo;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;

import static org.apache.tomcat.util.net.openssl.OpenSSLStatus.getName;

public class TaskCompletion {
    public static void main(String[] args) {

//        Process Engine initializes the Flowable engine.
//        Repository Service is accessed to handle process definition deployments.
//        Deployment uploads the process model to the Flowable engine.
//        Runtime Service is used to start process instances.
//        Process Instance tracks the execution of the running process.
//        Task Service allows you to interact with tasks in the running process.





//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        ProcessEngine processEngine = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("flowable.cfg.xml")
                .buildProcessEngine();
        System.out.println("ProcessEngine: " + processEngine);

        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/my-process.bpmn20.xml")
                .name("My Process Deployment")
                .deploy();
        System.out.println("Deployment ID: " + deployment.getId());

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Logger logger = Logger.getLogger(TaskCompletion.class.getName());


        // Get all tasks for the process instance
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().list();
        for(int i=1;i<=3;i++) {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess");
            System.out.println("Process Instance ID: " + processInstance.getId());
            taskService = processEngine.getTaskService();
            tasks = taskService.createTaskQuery().list();
            logger.info("Task List: " + tasks.size());
        }


        for (Task task : tasks) {
            System.out.println("Task Name: " + task.getName());
            System.out.println("Task ID: " + task.getId());

            // Complete the task
            taskService.complete(task.getId());
            System.out.println("Task completed!");
        }

        System.out.println("________________________________\nAll Tasks completed!!!");

    }
}
