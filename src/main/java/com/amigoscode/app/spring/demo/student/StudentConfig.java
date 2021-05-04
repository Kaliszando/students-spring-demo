package com.amigoscode.app.spring.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.time.LocalDate;
import java.time.Month;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
          Student a = new Student(
                  "Adam",
                  "adam@gmail.com",
                  LocalDate.of(1999, Month.JUNE, 2)
          );

          Student b = new Student(
                  "Beata",
                  "beata@gmail.com",
                  LocalDate.of(2000, Month.JUNE, 2)
          );

          repository.saveAll(List.of(a, b));
        };
    }
}
