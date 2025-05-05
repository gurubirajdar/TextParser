package com.textparser.config;

import com.textparser.model.SentenceCSV;
import com.textparser.processor.SentenceCSVProcessor;

import com.textparser.reader.SentenceReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class TextToCsvParserBatchConfig {

    @Autowired
    private SentenceCSVProcessor sentenceCSVProcessor;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public TextToCsvParserBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean(name="csvWriter")
    public FlatFileItemWriter<SentenceCSV> csvWriter() throws MalformedURLException {
        AtomicInteger count= new AtomicInteger(1);
        return  new FlatFileItemWriterBuilder<SentenceCSV>()
                .name("csvwriter")
                .resource(new FileUrlResource("src/main/resources/small-out.csv"))
                .delimited()
                .delimiter(",")
                .fieldExtractor(item -> new String[]{"Sentence"+ count.getAndIncrement() + Arrays.toString(item.getWords().toArray(new String[0]))})
                .build();

    }

    @Bean
    public Step textToCsvparserStep() throws IOException {
        return new StepBuilder("texttocsvstep",jobRepository)
                .<String, SentenceCSV>chunk(10,transactionManager)
                .reader(new SentenceReader(new ClassPathResource("small.in")))
                .processor(sentenceCSVProcessor)
                .writer(csvWriter())
                .build();
    }

    @Bean(name = "textToCsvJob")
    public Job textToCsvJob() throws IOException {
        return new JobBuilder("texttocsvjob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(textToCsvparserStep())
                .end()
                .build();
    }


}
