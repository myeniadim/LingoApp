package com.msku.ceng3505.lingoapp.models;

public class Vocabulary {
    private String englishVocab;
    private String turkishVocab;
    private Boolean isFavorite;
    private String docId;

    public Vocabulary(String englishVocab, String turkishVocab, Boolean isFavorite, String docId) {
        this.englishVocab = englishVocab;
        this.turkishVocab = turkishVocab;
        this.isFavorite = isFavorite;
        this.docId = docId;
    }

    public Vocabulary(){

    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getEnglishVocab() {
        return englishVocab;
    }

    public void setEnglishVocab(String englishVocab) {
        this.englishVocab = englishVocab;
    }

    public String getTurkishVocab() {
        return turkishVocab;
    }

    public void setTurkishVocab(String turkishVocab) {
        this.turkishVocab = turkishVocab;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
