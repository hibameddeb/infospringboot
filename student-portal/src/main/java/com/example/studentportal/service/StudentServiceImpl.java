package com.example.studentportal.service;

import com.example.studentportal.model.Student;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    // In-memory database using a Map
    private final Map<Long, Student> studentDb = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public StudentServiceImpl() {
        // Add some sample data
        Student student1 = new Student(idCounter.incrementAndGet(), "John Doe", 21,
                "john@example.com", "Computer Science", "CS123456");
        Student student2 = new Student(idCounter.incrementAndGet(), "Jane Smith", 22,
                "jane@example.com", "Mathematics", "MT789012");
        Student student3 = new Student(idCounter.incrementAndGet(), "Bob Johnson", 20,
                "bob@example.com", "Physics", "PH345678");

        studentDb.put(student1.getId(), student1);
        studentDb.put(student2.getId(), student2);
        studentDb.put(student3.getId(), student3);
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentDb.values());
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return Optional.ofNullable(studentDb.get(id));
    }

    @Override
    public Student saveStudent(Student student) {
        if (student.getId() == null) {
            // New student
            student.setId(idCounter.incrementAndGet());
        }
        studentDb.put(student.getId(), student);
        return student;
    }

    @Override
    public void deleteStudent(Long id) {
        studentDb.remove(id);
    }
    // In StudentServiceImpl.java
    @Override
    public List<Student> searchStudentsByName(String name) {
        return studentDb.values().stream()
                .filter(student -> student.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @Override
    public List<Student> getAllStudentsSorted(String sortField, String sortDir) {
        List<Student> students = getAllStudents(); // get unsorted list

        if (sortField == null || sortDir == null || sortField.isEmpty() || sortDir.isEmpty()) {
            return students;  // Return unsorted if any parameter is null or empty
        }

        Comparator<Student> comparator;

        switch (sortField.toLowerCase()) {  // Handle case insensitivity for sortField
            case "name":
                comparator = Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case "age":
                comparator = Comparator.comparing(Student::getAge);
                break;
            case "course":
                comparator = Comparator.comparing(Student::getCourse, String.CASE_INSENSITIVE_ORDER);
                break;
            default:
                return students;  // Default to unsorted list if sortField is invalid
        }

        // Handle sorting direction
        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        return students.stream()
                .sorted(comparator)
                .collect(Collectors.toList());  // collect() is a more typical use than toList() in older Java versions
    }

    @Override
    public List<Student> getPaginatedStudents(int page, int size, String sortField, String sortDir) {
        // Sort the students based on the provided sorting parameters
        List<Student> students = getAllStudentsSorted(sortField, sortDir);

        // Handle invalid page numbers (e.g., negative or out of bounds)
        if (page < 0) {
            page = 0; // Set to the first page
        }
        if (size <= 0) {
            size = 10; // Default page size
        }

        // Calculate the indices for pagination
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, students.size());

        if (fromIndex >= students.size()) {
            return new ArrayList<>(); // Return empty list if the page is out of bounds
        }

        // Return the sublist of students for the current page
        return students.subList(fromIndex, toIndex);
    }



}