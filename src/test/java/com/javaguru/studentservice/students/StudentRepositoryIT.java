package com.javaguru.studentservice.students;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //В случае H2 можно убрать
class StudentRepositoryIT {

    @Autowired
    private StudentRepository victim;

    @Test
    public void shouldSave() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName("TEST - 1");
        studentEntity.setQuote("Test Quote - 1");

        victim.save(studentEntity);

        assertThat(studentEntity.getId()).isNotNull();
    }

    @Test
    public void shouldFindAllByName() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName("TEST - 1");
        studentEntity.setQuote("Test Quote - 1");
        StudentEntity studentEntity2 = new StudentEntity();
        studentEntity2.setName("TEST - 1");
        studentEntity2.setQuote("Test Quote - 2");

        victim.save(studentEntity);
        victim.save(studentEntity2);

        List<StudentEntity> students = victim.findAllByName("TEST - 1");

        assertThat(students).extracting("name", "quote")
                .containsExactly(tuple(studentEntity.getName(), studentEntity.getQuote()),
                        tuple(studentEntity2.getName(), studentEntity2.getQuote()));
    }
}