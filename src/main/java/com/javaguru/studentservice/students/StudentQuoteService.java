package com.javaguru.studentservice.students;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class StudentQuoteService {

    private final RestTemplate restTemplate;

    StudentQuoteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    String retrieveRandomQuote() {
        StudentQuoteDto response = restTemplate.getForObject("https://programming-quotes-api.herokuapp.com/quotes/random", StudentQuoteDto.class);
        System.out.println("Received response: " + response);
        if (response == null) {
            throw new IllegalArgumentException("Failed to retrieve random student quote");
        }
        return response.getQuote();
    }
}
