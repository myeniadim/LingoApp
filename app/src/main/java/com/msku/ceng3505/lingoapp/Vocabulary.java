package com.msku.ceng3505.lingoapp;

public class Vocabulary {
    private String englishVocab;
    private String turkishVocab;

    public Vocabulary(String turkishVocab, String englishVocab) {
        this.turkishVocab = turkishVocab;
        this.englishVocab = englishVocab;
    }

    public String getEnglishVocab() {
        return englishVocab;
    }

    public String getTurkishVocab() {
        return turkishVocab;
    }
}
