package com.javaguru.studentservice.students;

import com.fasterxml.jackson.annotation.JsonProperty;

class StudentQuoteDto {

    private String id;
    @JsonProperty(value = "en")
    private String quote;
    private String author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "StudentQuoteDto{" +
                "id='" + id + '\'' +
                ", en='" + quote + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
