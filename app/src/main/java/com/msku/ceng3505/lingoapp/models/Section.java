package com.msku.ceng3505.lingoapp.models;

import java.io.Serializable;
import java.util.List;

public class Section implements Serializable{
    private String sectionId;
    private String title;
    private String difficultyLevel;
    private String readingHeader;
    private String readingContent;
    private List<Question> questions;

    public Section(){

    }

    public Section(String sectionId, String title, String difficultyLevel, String readingHeader, String readingContent, List<Question> questions) {
        this.sectionId = sectionId;
        this.title = title;
        this.difficultyLevel = difficultyLevel;
        this.readingHeader = readingHeader;
        this.readingContent = readingContent;
        this.questions = questions;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReadingHeader() {
        return readingHeader;
    }

    public void setReadingHeader(String readingHeader) {
        this.readingHeader = readingHeader;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getReadingContent() {
        return readingContent;
    }

    public void setReadingContent(String readingContent) {
        this.readingContent = readingContent;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
