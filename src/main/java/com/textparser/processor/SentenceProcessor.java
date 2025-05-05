package com.textparser.processor;


import com.textparser.model.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class SentenceProcessor implements ItemProcessor<String, Sentence> {

    private static Logger log = LoggerFactory.getLogger(SentenceProcessor.class);


    @Override
    public Sentence process(String item) throws Exception {
       // log.info("record line : "+item);
        String[] wordsArray = item.replace(".", "").split("\\s+");
        return new Sentence(Arrays.asList(wordsArray));
    }
}
