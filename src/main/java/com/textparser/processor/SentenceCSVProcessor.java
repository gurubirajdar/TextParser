package com.textparser.processor;

import com.textparser.model.Sentence;
import com.textparser.model.SentenceCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SentenceCSVProcessor implements ItemProcessor<String, SentenceCSV> {
    private static Logger log = LoggerFactory.getLogger(SentenceCSV.class);

    @Override
    public SentenceCSV process(String item) throws Exception {
        // log.info("record line : "+item);
        String[] wordsArray = item.replace(".", "").split("\\s+");
        return new SentenceCSV(Arrays.asList(wordsArray));
    }
}
