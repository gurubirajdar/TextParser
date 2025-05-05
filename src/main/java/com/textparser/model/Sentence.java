package com.textparser.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "Sentence")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sentence {

    @XmlElement(name = "word")
    private List<String> words = new ArrayList<String>();

    public Sentence() {}

    public Sentence(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
    @Override
    public String toString() {
        return "Sentence{" +
                "words=" + words +
                '}';
    }
}
