package com.msku.ceng3505.lingoapp.models;

public class UserProgress {
    private String sectionId;
    private int correct;
    private int incorrect;
    private int totalQuestion;


    public UserProgress(String sectionId, int correct, int incorrect, int totalQuestion) {
        this.sectionId = sectionId;
        this.correct = correct;
        this.incorrect = incorrect;
        this.totalQuestion = totalQuestion;
    }

    public UserProgress(){
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }
}
