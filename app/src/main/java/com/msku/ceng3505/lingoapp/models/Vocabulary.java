package com.msku.ceng3505.lingoapp.models;

public class Vocabulary {
    private String englishVocab;
    private String turkishVocab;
    private Boolean isFavorite;

    public Vocabulary(String englishVocab, String turkishVocab, Boolean isFavorite) {
        this.englishVocab = englishVocab;
        this.turkishVocab = turkishVocab;
        this.isFavorite = isFavorite;
    }

    public Vocabulary(){

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
