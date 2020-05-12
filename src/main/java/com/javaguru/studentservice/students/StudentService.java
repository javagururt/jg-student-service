package com.javaguru.studentservice.students;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class StudentService {

    private final StudentRepository studentRepository;
    private final StudentQuoteService studentQuoteService;

    StudentService(StudentRepository studentRepository, StudentQuoteService studentQuoteService) {
        this.studentRepository = studentRepository;
        this.studentQuoteService = studentQuoteService;
    }

    List<StudentDto> findByName(String name) {
        return studentRepository.findAllByName(name)
                .stream()
                .map(s -> new StudentDto(s.getId(), s.getName(), s.getQuote()))
                .collect(Collectors.toList());
    }

    StudentDto findById(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found, id: " + id));
        return new StudentDto(student.getId(), student.getName(), student.getQuote());
    }

    List<StudentDto> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(student -> new StudentDto(student.getId(), student.getName(), student.getQuote()))
                .collect(Collectors.toList());
    }

    StudentDto save(StudentDto dto) {
        String quote = studentQuoteService.retrieveRandomQuote();
        StudentEntity student = new StudentEntity();
        student.setName(dto.getName());
        student.setQuote(quote);
        studentRepository.save(student);
        return new StudentDto(student.getId(), student.getName(), student.getQuote());
    }
}
