package com.javaguru.studentservice.students;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Length;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;

class StudentDto {

    private Long id;
    @NotEmpty(message = "Student name must be not empty.")
    @Length(min = 3, max = 5, message = "Incorrect student name length.")
    private String name;
    private String quote;

    public StudentDto() {
    }

    public StudentDto(Long id,
                      String name,
                      String quote) {
        this.id = id;
        this.name = name;
        this.quote = quote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(quote, that.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quote);
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quote='" + quote + '\'' +
                '}';
    }
}
