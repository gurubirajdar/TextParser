package com.textparser.config;


import com.textparser.model.Sentence;
import com.textparser.processor.SentenceProcessor;
import com.textparser.reader.SentenceReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;


@Configuration
public class ParserBatchConfig {

    @Autowired
    private SentenceProcessor sentenceProcessor;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public ParserBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

   /* @Bean
    public FlatFileItemReader<String> reader() {
        FlatFileItemReader<String> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource("C:/Users/g.birajdar/workspace/TextToXmlCsv/src/main/resources/small.in"));
        *//*reader.setResource(new FileSystemResource("src/java/resources/large.in"));*//*
        reader.setStrict(false);
        reader.setLineMapper((line, lineNumber)->line);
        return reader;
    }*/

    @Bean
    public StaxEventItemWriter<Sentence> writer() {
        StaxEventItemWriter<Sentence> writer = new StaxEventItemWriter<>();
        writer.setResource(new FileSystemResource("src/main/resources/sentence_out.xml"));
        writer.setMarshaller(marshaller());
        writer.setRootTagName("sentence");
        writer.setOverwriteOutput(true);
        return writer;
    }

    @Bean(name = "marshaller")
    public  Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller marshaller= new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Sentence.class);
        return marshaller;
    }

    @Bean
    public Step parserStep() throws IOException {
        return new StepBuilder("texttoxmlstep",jobRepository)
                .<String, Sentence>chunk(10,transactionManager)
                .reader(new SentenceReader(new ClassPathResource("small.in")))
                .processor(sentenceProcessor)
                .writer(writer())
                .build();
    }

    @Bean(name = "parserTextToXmlJob")
    public Job textToXmlJob() throws IOException {
        return new JobBuilder("texttoxmljob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(parserStep())
                .end()
                .build();
    }


}
