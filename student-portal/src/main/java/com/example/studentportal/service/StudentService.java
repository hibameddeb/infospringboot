package com.example.studentportal.service;

import com.example.studentportal.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService  {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Long id);
    Student saveStudent(Student student);
    void deleteStudent(Long id);
    List<Student> searchStudentsByName(String name);
    List<Student> getAllStudentsSorted(String sortField, String sortDir);

    List<Student> getPaginatedStudents(int page, int size, String sortField, String sortDir);
}
