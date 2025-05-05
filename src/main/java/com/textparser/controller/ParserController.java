package com.textparser.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parser")
public class ParserController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("parserTextToXmlJob")
    private Job job;

    @GetMapping("/up")
    public String helloParser() {
        return "Hello parser application!";
    }

    @GetMapping("/texttoxml")
    public String textToXmlParser() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startJob", System.currentTimeMillis())
                //.addString("filePath", filePath)
                .toJobParameters();
        JobExecution run=jobLauncher.run(job,jobParameters);
        return run.getStatus().toString();
    }
}
