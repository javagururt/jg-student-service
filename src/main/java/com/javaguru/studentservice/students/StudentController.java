package com.javaguru.studentservice.students;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/students")
class StudentController {

    private final StudentService studentService;

    StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    StudentDto findById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @GetMapping
    List<StudentDto> findAll() {
        return studentService.findAll();
    }

    @GetMapping(params = "name")
    List<StudentDto> findByName(@RequestParam(name = "name") String name) {
        System.out.println("Received request - find by name: " + name);
        return studentService.findByName(name);
    }

    @PostMapping
    ResponseEntity<Void> create(@Valid @RequestBody StudentDto dto) {
        StudentDto createdStudent = studentService.save(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStudent.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(k -> ((FieldError) k).getField(),
                        v -> v.getDefaultMessage() != null ? v.getDefaultMessage() : "No message available"));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler
    void handleNotFoundException(StudentNotFoundException e) {
        System.out.println(e.getMessage());
    }

}
