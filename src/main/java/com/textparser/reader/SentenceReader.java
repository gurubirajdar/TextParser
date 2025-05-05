package com.textparser.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SentenceReader implements ItemReader<String> {

    private final BufferedReader reader;

    public SentenceReader(Resource resource) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
    }

    @Override
    public String read() throws Exception {
        StringBuilder sentence = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sentence.append(line).append(" ");
            if (line.trim().endsWith(".")) {
                return sentence.toString().trim();
            }
        }
        return null; // End of file
    }
}
