package com.textparser.model;

import java.util.List;

public class SentenceCSV {

    private List<String> words;

    public SentenceCSV(){

    }

    public SentenceCSV(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
