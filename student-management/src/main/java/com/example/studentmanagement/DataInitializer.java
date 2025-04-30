package com.example.studentmanagement;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(StudentRepository repository) {
        return args -> {
            // Only add test data if repository is empty
            if (repository.count() == 0) {
                repository.save(new Student());
                repository.save(new Student());
                repository.save(new Student());
                repository.save(new Student());
                repository.save(new Student());
                System.out.println("Sample data initialized!");
            }
        };
    }
}